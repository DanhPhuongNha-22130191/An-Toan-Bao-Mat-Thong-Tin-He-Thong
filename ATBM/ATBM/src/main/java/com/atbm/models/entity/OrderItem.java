package com.atbm.models.entity;

public class OrderItem {
    private long orderItemId;
    private long orderId;
    private long productId;
    private int quantity;
    private String nameSnapshot;
    private double priceSnapshot;
    private byte[] imageSnapshot;

    /**
     *  Lấy dữ liệu
     */
    public OrderItem(long orderItemId, long orderId, long productId, int quantity, String nameSnapshot, double priceSnapshot, byte[] imageSnapshot) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.nameSnapshot = nameSnapshot;
        this.priceSnapshot = priceSnapshot;
        this.imageSnapshot = imageSnapshot;
    }

    public OrderItem() {
    }

    /**
     * Tạo dữ liệu
     */
    public OrderItem(long orderId, long productId, int quantity, String nameSnapshot, double priceSnapshot, byte[] imageSnapshot) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.nameSnapshot = nameSnapshot;
        this.priceSnapshot = priceSnapshot;
        this.imageSnapshot = imageSnapshot;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNameSnapshot() {
        return nameSnapshot;
    }

    public void setNameSnapshot(String nameSnapshot) {
        this.nameSnapshot = nameSnapshot;
    }

    public double getPriceSnapshot() {
        return priceSnapshot;
    }

    public void setPriceSnapshot(double priceSnapshot) {
        this.priceSnapshot = priceSnapshot;
    }

    public byte[] getImageSnapshot() {
        return imageSnapshot;
    }

    public void setImageSnapshot(byte[] imageSnapshot) {
        this.imageSnapshot = imageSnapshot;
    }
}
