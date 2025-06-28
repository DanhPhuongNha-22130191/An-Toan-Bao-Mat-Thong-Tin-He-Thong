package com.atbm.services;

import com.atbm.dao.cart.CartDao;
import com.atbm.dao.order.OrderDao;
import com.atbm.dao.orderItem.OrderItemDao;
import com.atbm.dao.orderSecurity.OrderSecurityDao;
import com.atbm.dao.shippingInfo.ShippingInfoDao;
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
    private  OrderDao orderDao;
    private  ShippingInfoDao shippingInfoDao;
    private  OrderItemDao orderItemDao;
    private  OrderSecurityDao orderSecurityDao;
    private  CartDao cartDao;
    private  OrderBuilderHelper orderBuilderHelper;
    private  ExecuteSQLHelper executeSQLHelper;

    @Inject
    public OrderService(OrderDao orderDao, ShippingInfoDao shippingInfoDao, OrderItemDao orderItemDao, OrderSecurityDao orderSecurityDao, CartDao cartDao, OrderBuilderHelper orderBuilderHelper, ExecuteSQLHelper executeSQLHelper) {
        this.orderDao = orderDao;
        this.shippingInfoDao = shippingInfoDao;
        this.orderItemDao = orderItemDao;
        this.orderSecurityDao = orderSecurityDao;
        this.cartDao = cartDao;
        this.orderBuilderHelper = orderBuilderHelper;
        this.executeSQLHelper = executeSQLHelper;
    }

    public OrderService(){}

    public long checkout(long accountId, CheckoutOrderRequest checkoutOrderRequest, LocalDate updateAt) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        if (!updateAt.isEqual(cart.getUpdateAt()))
            throw new RuntimeException("Giỏ hàng đã thay đổi, vui lòng tải lại trang");

        // Chuẩn bị dữ liệu
        OrderSecurity orderSecurity = orderBuilderHelper.builderOrderSecurity(accountId, checkoutOrderRequest);
        ShippingInfo shippingInfo = orderBuilderHelper.builderShippingInfo(checkoutOrderRequest);

        // Bước chuẩn bị các bước transaction
        SQLTransactionStep<Long> securityStep = buildInsertOrderSecurityStep(orderSecurity);
        SQLTransactionStep<Long> shippingStep = buildInsertShippingInfoStep(shippingInfo);
        long[] id = new long[3];
        String paymentMethod = checkoutOrderRequest.getPaymentMethod();
        boolean success = executeSQLHelper.executeStepsInTransaction(List.of(connection -> {
            // Gọi step.apply(connection) để lấy ID và cache lại trong step
            id[0] = securityStep.apply(connection);
            return id[0];
        }, connection -> {
            // Gọi step.apply(connection) để lấy ID và cache lại trong step
            id[1] = shippingStep.apply(connection);
            return id[1];
        }, connection -> {
            SQLTransactionStep<Long> orderStep = buildInsertOrderStep(accountId, id[0], id[1], cart.getTotalPrice(), paymentMethod);
            id[2] = orderStep.apply(connection);
            return id[2];
        }, connection -> buildInsertOrderItemsStep(cart.getCartId(), id[2]).apply(connection), cartDao.clearCart(cart.getCartId()), cartDao.updateTotalPrice(accountId, 0.0)));

        if (!success) {
            throw new RuntimeException("Thanh toán thất bại");
        } else return id[2];
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

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderDao.getOrdersByStatus(status.name());
    }

    public void updateStatus(long orderId, OrderStatus status) {
        orderDao.updateStatus(orderId, status.name());
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

    private SQLTransactionStep<Long> buildInsertOrderStep(long accountId, long orderSecurityId, long shippingInfoId, double totalPrice, String paymentMethod) {
        Order order = orderBuilderHelper.builderOrder(accountId, orderSecurityId, shippingInfoId, totalPrice, paymentMethod);
        return new CachedTransactionStep<>(orderDao.insert(order));
    }

    private SQLTransactionStep<Boolean> buildInsertOrderItemsStep(long cartId, long orderId) {
        return connection -> {
            List<OrderItem> items = orderBuilderHelper.builderOrderItems(cartId, orderId);
            for (OrderItem item : items) {
                if (!orderItemDao.insert(item).apply(connection)) {
                    return false;
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

    public List<Order> getOrders(){
        return orderDao.getOrders();
    }
}
