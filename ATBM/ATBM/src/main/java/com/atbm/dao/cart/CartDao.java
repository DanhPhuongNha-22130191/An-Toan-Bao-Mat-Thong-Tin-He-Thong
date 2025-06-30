package com.atbm.dao.cart;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;

import java.util.List;

public interface CartDao {
    String CART_TABLE_NAME = "Cart";
    String CART_ID = "cartId";
    String ACCOUNT_ID = "accountId";
    String TOTAL_PRICE = "totalPrice";
    String UPDATED_AT = "updateAt";
    String CART_ITEM_TABLE_NAME = "CartItem";
    String CART_ITEM_ID = "cartItemId";
    String PRODUCT_ID = "productId";
    String QUANTITY = "quantity";
    String PRICE_SNAPSHOT = "priceSnapshot";
    String NAME_SNAPSHOT = "nameSnapshot";
    String IMAGE_SNAPSHOT = "imageSnapshot";

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
