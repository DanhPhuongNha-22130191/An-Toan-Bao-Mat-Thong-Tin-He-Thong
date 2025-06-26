package com.atbm.models.wrapper.response;

import com.atbm.models.entity.CartItem;

import java.time.LocalDate;
import java.util.List;

public record CartResponse(long cartId, double totalPrice, List<CartItem> items, LocalDate updateAt) {
}
