package com.atbm.services;

import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.models.OrderSecurity;
import com.atbm.utils.SignatureUtil;
import java.security.PublicKey;

public class OrderSecurityService {
    private OrderService orderService = new OrderService();

    public boolean isOrderTampered(Order order) {
        try {
            OrderSecurity security = orderService.getSecurity(order.getOrderId());
            if (security == null) return true;

            String data = generateOrderData(order); // Tạo chuỗi dữ liệu từ order
            PublicKey publicKey = SignatureUtil.getPublicKeyFromBase64(security.getPublicKey());
            return !SignatureUtil.verify(data, security.getSignature(), publicKey);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private String generateOrderData(Order order) {
        OrderDetail detail = order.getOrderDetail();
        StringBuilder data = new StringBuilder();
        data.append(order.getOrderId())
                .append(order.getOrderDate().toString())
                .append(order.getPaymentMethod())
                .append(detail.getFullName())
                .append(detail.getPhone())
                .append(detail.getEmail())
                .append(detail.getAddress())
                .append(order.getCartDTO().getItems().toString())
                .append(order.getVoucherId() != null ? order.getVoucherId() : "none");
        return data.toString();
    }
}