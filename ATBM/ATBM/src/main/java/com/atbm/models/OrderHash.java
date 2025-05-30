package com.atbm.models;

public class OrderHash {
    private long orderId;
    private String hash;

    public OrderHash(long orderId, String hash) {
        this.orderId = orderId;
        this.hash = hash;
    }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }
}