package com.atbm.models;


import com.atbm.dto.CartDTO;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Order {
    private long orderId;
    private long accountId;
    private double shipping;
    private String paymentMethod;
    private Long voucherId;
    private CartDTO cartDTO;
    private OrderDetail orderDetail;
    private OrderSecurity orderSecurity;
    private String status;
    private LocalDateTime orderDate;

    public Order() {
    }

    public Order(long accountId, double shipping, String paymentMethod, CartDTO cartDTO, OrderDetail orderDetail) {
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.cartDTO = cartDTO;
        this.orderDetail = orderDetail;
    }

    public Order(long accountId, double shipping, String paymentMethod) {
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
    }

    // ✅ Constructor này sửa voucherId -> Long để tránh lỗi null
    public Order(long orderId, long accountId, double shipping, String paymentMethod, Long voucherId) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.voucherId = voucherId;
    }

    // Getters and Setters
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    // ✅ Hỗ trợ set null
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartDTO cartDTO) {
        this.cartDTO = cartDTO;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public OrderSecurity getOrderSecurity() {
        return orderSecurity;
    }

    public void setOrderSecurity(OrderSecurity orderSecurity) {
        this.orderSecurity = orderSecurity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        if (orderDate != null) {
            this.orderDate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(orderDate.getTime()),
                    ZoneId.systemDefault()
            );
        }
    }

    public boolean isPaid() {
        return "PAID".equalsIgnoreCase(status);
    }

    public boolean isConfirmed() {
        return "CONFIRMED".equalsIgnoreCase(status);
    }

    public boolean isCancelled() {
        return "CANCELLED".equalsIgnoreCase(status);
    }

    // ✅ Tránh NullPointerException khi voucherId hoặc cartDTO là null
    public double getTotalAmount() {
        double total = 0;

        if (cartDTO != null) {
            total = cartDTO.getSubTotal();

            if (voucherId != null && cartDTO.getVoucher() != null) {
                total -= cartDTO.getVoucher().getPercentDecrease();
            }
//    public double getTotalAmount() {
//        double total = cartDTO.getSubTotal();
//        if (voucherId != null) {
//            total -= cartDTO.getVoucher().getPercentDecrease();
//        }
//        return total + shipping;
//    }
public double getTotalAmount() {
    double total = 0.0;
    if (cartDTO != null && cartDTO.getItems() != null) {
        total = cartDTO.getSubTotal();
        if (voucherId != null && cartDTO.getVoucher() != null) {
            try {
                total -= cartDTO.getVoucher().getPercentDecrease();
            } catch (Exception e) {
                System.err.println("Error applying voucher discount for orderId " + orderId + ": " + e.getMessage());
            }
        }

        return total + shipping;
    }
    public java.util.Date getOrderDateAsDate() {
        if (orderDate == null) return null;
        return java.util.Date.from(orderDate.atZone(ZoneId.systemDefault()).toInstant());
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", accountId=" + accountId +
                ", shipping=" + shipping +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", voucherId=" + voucherId +
                ", cartDTO=" + cartDTO +
                ", orderDetail=" + orderDetail +
                ", orderSecurity=" + orderSecurity +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
    }
    return total + shipping;
}
}
