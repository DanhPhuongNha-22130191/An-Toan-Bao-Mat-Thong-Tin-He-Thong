package com.atbm.models.wrapper.request;

public class AddCartRequest {
    private long productId;
    private int quantity;

    public AddCartRequest(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddCartRequest() {
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
