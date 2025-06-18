package com.atbm.services;

import com.atbm.dao.cart.CartDao;
import com.atbm.dao.cart.impl.CartDaoImpl;
import com.atbm.dao.product.ProductDao;
import com.atbm.dao.product.impl.ProductDaoImpl;
import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;
import com.atbm.models.wrapper.request.AddCartRequest;
import com.atbm.models.wrapper.request.UpdateCartRequest;
import com.atbm.models.wrapper.response.CartResponse;

import java.util.List;

public class CartService {
    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService() {
        cartDao = new CartDaoImpl();
        productDao = new ProductDaoImpl();
    }

    public CartResponse getCartByUserId(long accountId) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        List<CartItem> cartItems = cartDao.getCartItemsByCartId(cart.getCartId());
        return new CartResponse(cart.getCartId(), cart.getTotalPrice(), cartItems);
    }

    public void insertCart(Cart cart) {
        cartDao.insert(cart);
    }

    public void updateQuantity(long accountId, UpdateCartRequest updateCartRequest) {
        if (updateCartRequest.quantity() > 0)
            cartDao.updateQuantity(accountId, updateCartRequest.cartItemId(), updateCartRequest.quantity());
        else removeProductFromCart(accountId, updateCartRequest);
    }


    public void removeProductFromCart(long accountId, UpdateCartRequest updateCartRequest) {
        cartDao.removeProductFromCart(accountId, updateCartRequest.cartItemId());
    }

    public void addProductToCart(long accountId, AddCartRequest addCartRequest) {
        Product product = productDao.getProductById(accountId);
        cartDao.addProductToCart(accountId, product, addCartRequest.quantity());
    }

    public void clearCart(long accountId) {
        cartDao.clearCart(accountId);
    }
}
