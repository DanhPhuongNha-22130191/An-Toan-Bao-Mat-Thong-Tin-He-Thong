package com.atbm.dao.cart;

import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;

import java.util.List;

public interface CartDao {
    void insert(Cart cart);

    long getCartIdByAccountId(long accountId);

    Cart getCartByAccountId(long accountId);

    List<CartItem> getCartItemsByCartId(long cartId);

    void updateQuantity(long accountId, long cartItemId, int newQuantity);

    void addProductToCart(long accountId, Product product, int quantity);

    void removeProductFromCart(long accountId, long cartItemId);

    void clearCart(long accountId);
}
