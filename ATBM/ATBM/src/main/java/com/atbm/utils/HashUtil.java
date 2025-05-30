package com.atbm.utils;

import com.atbm.models.Order;
import java.security.MessageDigest;
import java.util.UUID;

public class HashUtil {
    public static String generateOrderHash(Order order) {
        String data = order.getOrderId() + order.getAccountId() + order.getShipping() +
                order.getPaymentMethod() + order.getTotalAmount() +
                (order.getOrderDate() != null ? order.getOrderDate().getTime() : "");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    public static String generateApiKey() {
        return UUID.randomUUID().toString();
    }
}