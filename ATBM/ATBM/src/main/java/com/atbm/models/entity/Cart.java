package com.atbm.models.entity;


import java.time.LocalDate;

public class Cart {
    private long cartId;
    private long accountId;
    private double totalPrice;
    private LocalDate updateAt;

    /**
     * Lấy dữ liệu
     */
    public Cart(long cartId, long accountId,double totalPrice, LocalDate updateAt) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.accountId = accountId;
        this.updateAt = updateAt;
    }

    /**
     * Thêm dữ liệu
     */
    public Cart(long accountId) {
        this.accountId = accountId;
        this.totalPrice = 0;
        this.updateAt = LocalDate.now();
    }
    public Cart() {
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
}
