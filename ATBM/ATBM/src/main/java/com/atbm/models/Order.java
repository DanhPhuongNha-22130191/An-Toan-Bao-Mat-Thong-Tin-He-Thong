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

    public Order(long accountId, double shipping, String paymentMethod, CartDTO cartDTO,
                 OrderDetail orderDetail) {
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.cartDTO = cartDTO;
        this.orderDetail = orderDetail;
    }

    public OrderSecurity getOrderSecurity() {
        return orderSecurity;
    }

    public void setOrderSecurity(OrderSecurity orderSecurity) {
        this.orderSecurity = orderSecurity;
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

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public Order(long accountId, double shipping, String paymentMethod) {
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
    }

    public Order(long orderId, long accountId, double shipping, String paymentMethod, long voucherId) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.voucherId = voucherId;
    }

    public Order() {
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
        this.orderDate = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(orderDate.getTime()),
                ZoneId.systemDefault()
        );
    }

    public boolean isPaid() {
        return "PAID".equals(status);
    }

    public boolean isConfirmed() {
        return "CONFIRMED".equals(status);
    }

    public boolean isCancelled() {
        return "CANCELLED".equals(status);
    }

    public double getTotalAmount() {
        double total = cartDTO.getSubTotal();
        if (voucherId != null) {
            total -= cartDTO.getVoucher().getPercentDecrease();
        }
        return total + shipping;
    }
}
