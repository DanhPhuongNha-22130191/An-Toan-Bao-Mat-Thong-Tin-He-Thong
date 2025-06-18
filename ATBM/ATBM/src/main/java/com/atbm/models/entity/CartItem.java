package com.atbm.models.entity;

public class CartItem {
    private long cartItemId;
    private long cartId;
    private long productId;
    private int quantity;
    private double priceSnapshot;
    private String nameSnapshot;
    private byte[] imageSnapshot;

    public CartItem() {
    }

    /**
     *  Lấy dữ liệu
     */
    public CartItem(long cartItemId, long cartId, long productId, int quantity, double priceSnapshot, String nameSnapshot, byte[] imageSnapshot) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
        this.nameSnapshot = nameSnapshot;
        this.imageSnapshot = imageSnapshot;
    }

    /**
     * Thêm dữ liệu
     */
    public CartItem(long cartId, long productId, int quantity, double priceSnapshot, String nameSnapshot, byte[] imageSnapshot) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
        this.nameSnapshot = nameSnapshot;
        this.imageSnapshot = imageSnapshot;
    }

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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

    public double getPriceSnapshot() {
        return priceSnapshot;
    }

    public void setPriceSnapshot(double priceSnapshot) {
        this.priceSnapshot = priceSnapshot;
    }

    public String getNameSnapshot() {
        return nameSnapshot;
    }

    public void setNameSnapshot(String nameSnapshot) {
        this.nameSnapshot = nameSnapshot;
    }

    public byte[] getImageSnapshot() {
        return imageSnapshot;
    }

    public void setImageSnapshot(byte[] imageSnapshot) {
        this.imageSnapshot = imageSnapshot;
    }
}
