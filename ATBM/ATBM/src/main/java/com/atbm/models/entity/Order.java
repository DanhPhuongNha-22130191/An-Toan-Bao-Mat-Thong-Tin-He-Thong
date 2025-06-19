package com.atbm.models.entity;

import com.atbm.models.enums.OrderStatus;
import com.atbm.models.enums.PaymentMethod;

import java.time.LocalDateTime;

public class Order {
    private long orderId;
    private long accountId;
    private long orderSecurityId;
    private long shippingInfoId;
    private double totalPrice;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime orderAt;

    /**
     *  Lấy dữ liệu
     */
    public Order(long orderId, long accountId, long orderSecurityId, long shippingInfoId, double totalPrice, OrderStatus status, PaymentMethod paymentMethod, LocalDateTime orderAt) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.orderSecurityId = orderSecurityId;
        this.shippingInfoId = shippingInfoId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.orderAt = orderAt;
    }

    public Order() {
    }

    /**
     *  Tạo dữ liệu
     */
    public Order(long shippingInfoId, long orderSecurityId, long accountId, double totalPrice, PaymentMethod paymentMethod, OrderStatus status, LocalDateTime orderAt) {
        this.orderAt = orderAt;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.totalPrice = totalPrice;
        this.shippingInfoId = shippingInfoId;
        this.orderSecurityId = orderSecurityId;
        this.accountId = accountId;
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

    public long getOrderSecurityId() {
        return orderSecurityId;
    }

    public void setOrderSecurityId(long orderSecurityId) {
        this.orderSecurityId = orderSecurityId;
    }

    public long getShippingInfoId() {
        return shippingInfoId;
    }

    public void setShippingInfoId(long shippingInfoId) {
        this.shippingInfoId = shippingInfoId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }
}
