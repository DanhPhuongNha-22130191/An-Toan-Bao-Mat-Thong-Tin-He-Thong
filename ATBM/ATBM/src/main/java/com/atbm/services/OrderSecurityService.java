package com.atbm.services;

import com.atbm.dao.orderItem.OrderItemDao;
import com.atbm.dao.orderSecurity.OrderSecurityDao;
import com.atbm.dao.shippingInfo.ShippingInfoDao;
import com.atbm.models.entity.Order;
import com.atbm.models.entity.OrderItem;
import com.atbm.models.entity.OrderSecurity;
import com.atbm.models.entity.ShippingInfo;
import com.atbm.utils.LogUtils;
import com.atbm.utils.SignatureUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.security.PublicKey;
import java.util.List;

@ApplicationScoped
public class OrderSecurityService {
    private final OrderItemDao orderItemDao;
    private final OrderSecurityDao orderSecurityDao;
    private final ShippingInfoDao shippingInfoDao;

    @Inject
    public OrderSecurityService(
            OrderItemDao orderItemDao,
            OrderSecurityDao orderSecurityDao,
            ShippingInfoDao shippingInfoDao) {
        this.orderItemDao = orderItemDao;
        this.orderSecurityDao = orderSecurityDao;
        this.shippingInfoDao = shippingInfoDao;
    }

    public OrderSecurityService() {
        orderSecurityDao = null;
        orderItemDao = null;
        shippingInfoDao = null;
    }

    public boolean isOrderTampered(Order order) {
        try {
            OrderSecurity security = orderSecurityDao.getOrderSecurityById(order.getOrderSecurityId());
            if (security == null) {
                return true; // Không tìm thấy security record
            }

            // Kiểm tra có chữ ký không
            if (security.getSignature() == null || security.getSignature().trim().isEmpty()) {
                return true; // Chưa ký
            }

            // Kiểm tra có public key không
            if (security.getPublicKey() == null || security.getPublicKey().trim().isEmpty()) {
                return true; // Không có public key
            }

            String data = generateOrderData(order);
            PublicKey publicKey = SignatureUtils.getPublicKeyFromBase64(security.getPublicKey());
            boolean isTampered = !SignatureUtils.verify(data, security.getSignature(), publicKey);

            if (isTampered) {
                orderSecurityDao.clearInformation(order.getOrderSecurityId());
            }
            return isTampered;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateOrderData(Order order) {
        ShippingInfo shippingInfo = shippingInfoDao.getShippingInfoById(order.getShippingInfoId());
        List<OrderItem> orderItems = orderItemDao.getOrderItemsByOrderId(order.getOrderId());

        StringBuilder data = new StringBuilder();

        // Format ngày đặt theo định dạng Việt Nam
        String orderDate = order.getOrderAt() != null ?
                order.getOrderAt().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) :
                "none";

        // Thông tin cơ bản đơn hàng
        data.append("Ngày đặt: ").append(orderDate).append("\n");
        data.append("Họ tên: ").append(shippingInfo != null ? shippingInfo.getRecipientName() : "none").append("\n");
        data.append("SĐT: ").append(shippingInfo != null ? shippingInfo.getPhoneNumber() : "none").append("\n");

        // Địa chỉ giao hàng
        if (shippingInfo != null) {
            data.append("Địa chỉ: ")
                    .append(shippingInfo.getAddressLine() != null ? shippingInfo.getAddressLine() : "")
                    .append(", ")
                    .append(shippingInfo.getWard() != null ? shippingInfo.getWard() : "")
                    .append(", ")
                    .append(shippingInfo.getDistrict() != null ? shippingInfo.getDistrict() : "")
                    .append(", ")
                    .append(shippingInfo.getProvince() != null ? shippingInfo.getProvince() : "")
                    .append("\n");
        } else {
            data.append("Địa chỉ: none\n");
        }

        // Danh sách sản phẩm
        data.append("Sản phẩm:\n");
        if (orderItems != null && !orderItems.isEmpty()) {
            for (OrderItem item : orderItems) {
                data.append("• ")
                        .append(item.getNameSnapshot() != null ? item.getNameSnapshot() : "none")
                        .append(" x")
                        .append(item.getQuantity())
                        .append(": ")
                        .append(item.getPriceSnapshot() > 0 ? item.getPriceSnapshot() * item.getQuantity() : 0)
                        .append(" VNĐ\n");
            }
        } else {
            data.append("  none\n");
        }

        // Tổng tiền
        data.append("Tổng tiền: ")
                .append(order.getTotalPrice() > 0 ? order.getTotalPrice() : 0)
                .append(" VNĐ");
        return data.toString();
    }
}