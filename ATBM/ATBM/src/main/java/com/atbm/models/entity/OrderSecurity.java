package com.atbm.models.entity;

public class OrderSecurity {
    private long orderSecurityId;
    private String publicKey;
    private String signature;

    /**
     *  Lấy dữ liệu
     */
    public OrderSecurity(long orderSecurityId, String publicKey, String signature) {
        this.orderSecurityId = orderSecurityId;
        this.publicKey = publicKey;
        this.signature = signature;
    }
    public OrderSecurity() {
    }
    /**
     *  Tạo dữ liệu
     */
    public OrderSecurity(String signature, String publicKey) {
        this.signature = signature;
        this.publicKey = publicKey;
    }

    public long getOrderSecurityId() {
        return orderSecurityId;
    }

    public void setOrderSecurityId(long orderSecurityId) {
        this.orderSecurityId = orderSecurityId;
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
}
