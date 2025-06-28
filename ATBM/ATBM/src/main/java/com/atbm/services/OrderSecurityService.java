package com.atbm.services;

import com.atbm.dao.order.OrderDao;
import com.atbm.dao.orderItem.OrderItemDao;
import com.atbm.dao.orderSecurity.OrderSecurityDao;
import com.atbm.dao.shippingInfo.ShippingInfoDao;
import com.atbm.models.entity.Order;
import com.atbm.models.entity.OrderItem;
import com.atbm.models.entity.OrderSecurity;
import com.atbm.models.entity.ShippingInfo;
import com.atbm.utils.SignatureUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class OrderSecurityService {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final OrderSecurityDao orderSecurityDao;
    private final ShippingInfoDao shippingInfoDao;

    @Inject
    public OrderSecurityService(OrderDao orderDao,
                                OrderItemDao orderItemDao,
                                OrderSecurityDao orderSecurityDao,
                                ShippingInfoDao shippingInfoDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.orderSecurityDao = orderSecurityDao;
        this.shippingInfoDao = shippingInfoDao;
    }

    public boolean isOrderTampered(Order order) {
        try {
            OrderSecurity security = orderSecurityDao.getOrderSecurityById(order.getOrderSecurityId());
            if (security == null) {
                return true;
            }

            String data = generateOrderData(order);
            PublicKey publicKey = SignatureUtils.getPublicKeyFromBase64(security.getPublicKey());
            return !SignatureUtils.verify(data, security.getSignature(), publicKey);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private String generateOrderData(Order order) {
        ShippingInfo shippingInfo = shippingInfoDao.getShippingInfoById(order.getShippingInfoId());
        List<OrderItem> orderItems = orderItemDao.getOrderItemsByOrderId(order.getOrderId());

        StringBuilder data = new StringBuilder();
        data.append(order.getOrderId())
                .append(order.getOrderAt() != null ? order.getOrderAt().toString() : "none")
                .append(order.getPaymentMethod() != null ? order.getPaymentMethod().toString() : "none")
                .append(shippingInfo != null ? shippingInfo.getRecipientName() : "none")
                .append(shippingInfo != null ? shippingInfo.getPhoneNumber() : "none")
                .append("none") // Email not available in ShippingInfo
                .append(shippingInfo != null ? shippingInfo.getAddressLine() : "none")
                .append(shippingInfo != null ? shippingInfo.getDistrict() : "none")
                .append(shippingInfo != null ? shippingInfo.getProvince() : "none")
                .append(shippingInfo != null ? shippingInfo.getWard() : "none")
                .append(orderItems != null ? orderItems.toString() : "none");
        return data.toString();
    }
}