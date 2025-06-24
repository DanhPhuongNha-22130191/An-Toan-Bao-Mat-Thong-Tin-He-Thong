package com.atbm.services;

import com.atbm.dao.account.AccountDao;
import com.atbm.dao.account.impl.AccountDaoImpl;
import com.atbm.dao.cart.CartDao;
import com.atbm.dao.cart.impl.CartDaoImpl;
import com.atbm.dao.order.OrderDao;
import com.atbm.dao.order.impl.OrderDaoImpl;
import com.atbm.dao.orderItem.OrderItemDao;
import com.atbm.dao.orderItem.impl.OrderItemDaoImpl;
import com.atbm.dao.orderSecurity.OrderSecurityDao;
import com.atbm.dao.orderSecurity.impl.OrderSecurityDaoImpl;
import com.atbm.dao.shippingInfo.ShippingInfoDao;
import com.atbm.dao.shippingInfo.impl.ShippingInfoDaoImpl;
import com.atbm.database.CachedTransactionStep;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.helper.OrderBuilderHelper;
import com.atbm.models.entity.*;
import com.atbm.models.enums.OrderStatus;
import com.atbm.models.wrapper.request.CheckoutOrderRequest;
import com.atbm.models.wrapper.response.OrderResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {
    private final OrderDao orderDao;
    private final ShippingInfoDao shippingInfoDao;
    private final OrderItemDao orderItemDao;
    private final OrderSecurityDao orderSecurityDao;
    private final CartDao cartDao;
    private final AccountDao accountDao;
    private final OrderBuilderHelper orderBuilderHelper;
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public OrderService(OrderDao orderDao,
                        ShippingInfoDao shippingInfoDao,
                        OrderItemDao orderItemDao,
                        OrderSecurityDao orderSecurityDao,
                        CartDao cartDao,
                        AccountDao accountDao,
                        OrderBuilderHelper orderBuilderHelper, ExecuteSQLHelper executeSQLHelper) {
        this.orderDao = orderDao;
        this.shippingInfoDao = shippingInfoDao;
        this.orderItemDao = orderItemDao;
        this.orderSecurityDao = orderSecurityDao;
        this.cartDao = cartDao;
        this.accountDao = accountDao;
        this.orderBuilderHelper = orderBuilderHelper;
        this.executeSQLHelper = executeSQLHelper;
    }

    public void checkout(long accountId, CheckoutOrderRequest checkoutOrderRequest, LocalDate updateAt) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        if (!updateAt.isEqual(cart.getUpdateAt()))
            throw new RuntimeException("Giỏ hàng đã thay đổi, vui lòng tải lại trang");

        // Chuẩn bị dữ liệu
        OrderSecurity orderSecurity = orderBuilderHelper.builderOrderSecurity(accountId, checkoutOrderRequest);
        ShippingInfo shippingInfo = orderBuilderHelper.builderShippingInfo(checkoutOrderRequest);

        // Bước chuẩn bị các bước transaction
        SQLTransactionStep<Long> securityStep = buildInsertOrderSecurityStep(orderSecurity);
        SQLTransactionStep<Long> shippingStep = buildInsertShippingInfoStep(shippingInfo);
        SQLTransactionStep<Long> orderStep = buildInsertOrderStep(accountId, orderSecurity, shippingInfo, cart.getTotalPrice(), checkoutOrderRequest.paymentMethod());

        boolean success = executeSQLHelper.executeStepsInTransaction(List.of(
                connection -> {
                    // Gọi step.apply(connection) để lấy ID và cache lại trong step
                    orderSecurity.setOrderSecurityId(securityStep.apply(connection));
                    return securityStep;
                },
                connection -> {
                    // Gọi step.apply(connection) để lấy ID và cache lại trong step
                    shippingInfo.setShippingInfoId(shippingStep.apply(connection));
                    return shippingStep;
                },
                connection -> orderStep
                ,
                buildInsertOrderItemsStep(cart.getCartId(), orderStep),
                cartDao.clearCart(cart.getCartId()),
                cartDao.updateTotalPrice(accountId, 0.0)
        ));

        if (!success) {
            throw new RuntimeException("Thanh toán thất bại");
        }
    }


    public List<OrderItem> getOrderItemsByOrderId(long orderId) {
        return orderItemDao.getOrderItemsByOrderId(orderId);
    }

    public OrderSecurity getOrderSecurityById(long orderSecurityId) {
        return orderSecurityDao.getOrderSecurityById(orderSecurityId);
    }

    public OrderResponse getOrderById(long orderId, long accountId) {
        Order order = orderDao.getOrderById(accountId, orderId);
        return createOrderResponse(order);
    }

    public List<OrderResponse> getOrdersByAccountId(long accountId) {
        List<Order> orders = orderDao.getOrdersByAccountId(accountId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(createOrderResponse(order));
        }
        return orderResponses;
    }

    private OrderResponse createOrderResponse(Order order) {
        ShippingInfo shippingInfo = shippingInfoDao.getShippingInfoById(order.getShippingInfoId());
        boolean isDigitallySigned = order.getOrderSecurityId() != 0 && orderSecurityDao.isDigitallySigned(order.getOrderSecurityId());
        List<OrderItem> orderItems = orderItemDao.getOrderItemsByOrderId(order.getOrderId());
        return new OrderResponse(order, shippingInfo, orderItems, isDigitallySigned);
    }

    private SQLTransactionStep<Long> buildInsertOrderSecurityStep(OrderSecurity orderSecurity) {
        return new CachedTransactionStep<>(orderSecurityDao.insert(orderSecurity));
    }

    private SQLTransactionStep<Long> buildInsertShippingInfoStep(ShippingInfo shippingInfo) {
        return new CachedTransactionStep<>(shippingInfoDao.insert(shippingInfo));
    }

    private SQLTransactionStep<Long> buildInsertOrderStep(long accountId, OrderSecurity orderSecurity, ShippingInfo shippingInfo, double totalPrice, String paymentMethod) {
        Order order = orderBuilderHelper.builderOrder(accountId, orderSecurity.getOrderSecurityId(), shippingInfo.getShippingInfoId(), totalPrice, paymentMethod);
        return new CachedTransactionStep<>(orderDao.insert(order));
    }

    private SQLTransactionStep<Boolean> buildInsertOrderItemsStep(long cartId, SQLTransactionStep<Long> orderStep) {
        return connection -> {
            long orderId = orderStep.apply(connection); // Lấy lại ID
            List<OrderItem> items = orderBuilderHelper.builderOrderItems(cartId, orderId);
            for (OrderItem item : items) {
                if (!orderItemDao.insert(item).apply(connection)) {
                    return null;
                }
            }
            return true;
        };
    }

    public void updateSignature(long id, long orderId, String signature) {
        if (signature == null) {
            throw new RuntimeException("Chữ ký không được bỏ trống");
        }
        Order order = orderDao.getOrderById(orderId, id);
        orderSecurityDao.updateSignature(order.getOrderSecurityId(), signature);
    }
}
