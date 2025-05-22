package com.atbm.models;


import com.atbm.dto.CartDTO;

import java.time.LocalDateTime;

public class Order {
    private long orderId;
    private long accountId;
    private double shipping;
    private String paymentMethod;
    private Long voucherId;
    private String signature;
    private String publicKey;
    private CartDTO cartDTO;
    private OrderDetail orderDetail;
    private String hash;


    public Order(long accountId, double shipping, String paymentMethod, CartDTO cartDTO,
                 OrderDetail orderDetail) {
        this.accountId = accountId;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.cartDTO = cartDTO;
        this.orderDetail = orderDetail;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    private String status;
    private LocalDateTime orderDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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
