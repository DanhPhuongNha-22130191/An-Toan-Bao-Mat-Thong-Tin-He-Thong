package com.atbm.helper;

import com.atbm.dao.account.AccountDao;
import com.atbm.dao.cart.CartDao;
import com.atbm.models.entity.*;
import com.atbm.models.enums.PaymentMethod;
import com.atbm.models.wrapper.request.CheckoutOrderRequest;
import com.atbm.services.LocationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderBuilderHelper {
    private final AccountDao accountDao;
    private final CartDao cartDao;
    private final LocationService locationService;

    @Inject
    public OrderBuilderHelper(AccountDao accountDao, CartDao cartDao, LocationService locationService) {
        this.accountDao = accountDao;
        this.cartDao = cartDao;
        this.locationService = locationService;
    }


    public OrderSecurity builderOrderSecurity(long accountId, CheckoutOrderRequest checkoutOrderRequest) {
        OrderSecurity orderSecurity = new OrderSecurity();
        String publicKey = accountDao.getPublicKeyActive(accountId);
        orderSecurity.setPublicKey(publicKey);
        orderSecurity.setSignature(checkoutOrderRequest.signature());
        return orderSecurity;
    }

    public ShippingInfo builderShippingInfo(CheckoutOrderRequest checkoutOrderRequest) {
        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.setNote(checkoutOrderRequest.note());
        shippingInfo.setPhoneNumber(checkoutOrderRequest.phoneNumber());
        shippingInfo.setShippingMethod(checkoutOrderRequest.shippingMethod());
        shippingInfo.setRecipientName(checkoutOrderRequest.recipientName());
        builderLocation(shippingInfo, checkoutOrderRequest);
        return shippingInfo;
    }

    //Tạo thông tin địa chỉ
    private void builderLocation(ShippingInfo shippingInfo, CheckoutOrderRequest checkoutOrderRequest) {
        shippingInfo.setAddressLine(checkoutOrderRequest.addressLine());
        shippingInfo.setDistrict(locationService.getDistrictName(checkoutOrderRequest.districtId()));
        shippingInfo.setDistrictId(checkoutOrderRequest.districtId());
        shippingInfo.setWardId(checkoutOrderRequest.wardId());
        shippingInfo.setWard(locationService.getWardName(checkoutOrderRequest.wardId()));
        shippingInfo.setProvince(locationService.getProvinceName(checkoutOrderRequest.provinceId()));
        shippingInfo.setProvinceId(checkoutOrderRequest.provinceId());
    }

    public Order builderOrder(long accountId, long orderSecurityId, long shippingInfoId, double totalPrice, String paymentMethod) {
        Order order = new Order();
        order.setAccountId(accountId);
        order.setOrderSecurityId(orderSecurityId);
        order.setShippingInfoId(shippingInfoId);
        order.setTotalPrice(totalPrice);
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
        return order;
    }

    public List<OrderItem> builderOrderItems(long cartId, long orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = cartDao.getCartItemsByCartId(cartId);
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setImageSnapshot(cartItem.getImageSnapshot());
            orderItem.setNameSnapshot(cartItem.getNameSnapshot());
            orderItem.setPriceSnapshot(cartItem.getPriceSnapshot());

            orderItems.add(orderItem);
        }
        return orderItems;
    }

}
