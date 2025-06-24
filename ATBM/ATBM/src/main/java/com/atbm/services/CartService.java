package com.atbm.services;

import com.atbm.dao.cart.CartDao;
import com.atbm.dao.product.ProductDao;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;
import com.atbm.models.wrapper.request.AddCartRequest;
import com.atbm.models.wrapper.request.UpdateCartRequest;
import com.atbm.models.wrapper.response.CartResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CartService {
    private final CartDao cartDao;
    private final ProductDao productDao;
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public CartService(CartDao cartDao, ProductDao productDao, ExecuteSQLHelper executeSQLHelper) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.executeSQLHelper = executeSQLHelper;
    }


    public CartResponse getCartByAccountId(long accountId) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        List<CartItem> cartItems = cartDao.getCartItemsByCartId(cart.getCartId());
        return new CartResponse(cart.getCartId(), cart.getTotalPrice(), cartItems,cart.getUpdateAt());
    }

    public void insertCart(Cart cart) {
        cartDao.insert(cart);
    }

    public void updateQuantity(long accountId, UpdateCartRequest updateCartRequest) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        CartItem cartItem = cartDao.getCartItemById(updateCartRequest.cartItemId());

        double newTotalPrice = calculateNewTotalPrice(cart.getTotalPrice(), cartItem.getQuantity(), updateCartRequest.quantity(), cartItem.getPriceSnapshot());

        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.updateQuantity(cart.getCartId(), updateCartRequest.cartItemId(), updateCartRequest.quantity()),
                cartDao.updateTotalPrice(accountId, newTotalPrice))))
            throw new RuntimeException("Thay đổi số lượng hàng thất bại");
    }


    public void removeProductFromCart(long accountId, UpdateCartRequest updateCartRequest) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        CartItem cartItem = cartDao.getCartItemById(updateCartRequest.cartItemId());

        double productAmount = cartItem.getPriceSnapshot() * cartItem.getQuantity();
        double newTotalPrice = cart.getTotalPrice() - productAmount;

        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.removeProductFromCart(cart.getCartId(), updateCartRequest.cartItemId()),
                cartDao.updateTotalPrice(accountId, newTotalPrice))))
            throw new RuntimeException("Xóa hàng khỏi giỏ hàng thất bại");
    }

    public void addProductToCart(long accountId, AddCartRequest addCartRequest) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        Product product = productDao.getProductById(addCartRequest.productId());

        double priceToAdd = product.getPrice() * addCartRequest.quantity();
        double newTotalPrice = cart.getTotalPrice() + priceToAdd;
        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.addProductToCart(cart.getCartId(), product, addCartRequest.quantity()),
                cartDao.updateTotalPrice(accountId, newTotalPrice))))
            throw new RuntimeException("Thêm vào giỏ hàng thất bại");
    }

    public void clearCart(long accountId) {
        long cartId = cartDao.getCartIdByAccountId(accountId);
        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.clearCart(cartId),
                cartDao.updateTotalPrice(accountId, 0.0))))
            throw new RuntimeException("Dọn dẹp giỏ hàng thất bại");
    }

    private double calculateNewTotalPrice(double currentTotal, int oldQuantity, int newQuantity, double price) {
        int quantityDelta = newQuantity - oldQuantity;
        return currentTotal + quantityDelta * price;
    }
}
