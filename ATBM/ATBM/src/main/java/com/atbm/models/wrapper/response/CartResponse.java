package com.atbm.models.wrapper.response;

import com.atbm.models.entity.CartItem;

import java.time.LocalDate;
import java.util.List;

public class CartResponse {
    private long cartId;
    private double totalPrice;
    private List<CartItem> items;
    private LocalDate updateAt;

    public CartResponse() {
    }

    public CartResponse(long cartId, double totalPrice, List<CartItem> items, LocalDate updateAt) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.items = items;
        this.updateAt = updateAt;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }
    public String getTotalPriceStringWithCurrency() {
        return String.format("%.2f VND", getTotalPrice());
    }
}
