package com.atbm.models;

public class OrderSecurity {
    private Long orderId;
    private String signature;
    private String publicKey;

    public OrderSecurity(String signature, String publicKey) {
        this.signature = signature;
        this.publicKey = publicKey;
    }

    public OrderSecurity() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
