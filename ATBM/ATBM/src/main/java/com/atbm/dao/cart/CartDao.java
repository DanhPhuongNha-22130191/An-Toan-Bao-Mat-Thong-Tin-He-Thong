package com.atbm.dao.cart;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;

import java.util.List;

public interface CartDao {
    void insert(Cart cart);

    long getCartIdByAccountId(long accountId);

    Cart getCartByAccountId(long accountId);

    List<CartItem> getCartItemsByCartId(long cartId);

    CartItem getCartItemById(long cartItemId);

    SQLTransactionStep<Boolean> updateTotalPrice(long accountId, double newTotalPrice);

    SQLTransactionStep<Boolean> updateQuantity(long cartId, long cartItemId, int newQuantity);

    SQLTransactionStep<Boolean> addProductToCart(long cartId, Product product, int quantity);

    SQLTransactionStep<Boolean> removeProductFromCart(long cartId, long cartItemId);

    SQLTransactionStep<Boolean> clearCart(long cartId);
}
