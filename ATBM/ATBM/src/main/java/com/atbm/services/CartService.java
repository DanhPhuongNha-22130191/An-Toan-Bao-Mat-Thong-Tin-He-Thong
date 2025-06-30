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

    public CartService() {
        cartDao = null;
        productDao = null;
        executeSQLHelper = null;
    }

    @Inject
    public CartService(CartDao cartDao, ProductDao productDao, ExecuteSQLHelper executeSQLHelper) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.executeSQLHelper = executeSQLHelper;
    }


    public CartResponse getCartByAccountId(long accountId) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        if (cart != null) {
            List<CartItem> cartItems = cartDao.getCartItemsByCartId(cart.getCartId());
            return new CartResponse(cart.getCartId(), cart.getTotalPrice(), cartItems, cart.getUpdateAt());
        } else throw new RuntimeException("Không lấy được giỏ hàng");

    }

    public void insertCart(Cart cart) {
        cartDao.insert(cart);
    }

    public void updateQuantity(long accountId, UpdateCartRequest updateCartRequest) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        CartItem cartItem = cartDao.getCartItemById(updateCartRequest.getCartItemId());

        double newTotalPrice = calculateNewTotalPrice(cart.getTotalPrice(), cartItem.getQuantity(), updateCartRequest.getQuantity(), cartItem.getPriceSnapshot());

        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.updateQuantity(cart.getCartId(), updateCartRequest.getCartItemId(), updateCartRequest.getQuantity()),
                cartDao.updateTotalPrice(accountId, newTotalPrice))))
            throw new RuntimeException("Thay đổi số lượng hàng thất bại");
    }


    public void removeProductFromCart(long accountId, UpdateCartRequest updateCartRequest) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        CartItem cartItem = cartDao.getCartItemById(updateCartRequest.getCartItemId());

        double productAmount = cartItem.getPriceSnapshot() * cartItem.getQuantity();
        double newTotalPrice = cart.getTotalPrice() - productAmount;

        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.removeProductFromCart(cart.getCartId(), updateCartRequest.getCartItemId()),
                cartDao.updateTotalPrice(accountId, newTotalPrice))))
            throw new RuntimeException("Xóa hàng khỏi giỏ hàng thất bại");
    }

    public void addProductToCart(long accountId, AddCartRequest addCartRequest) {
        Cart cart = cartDao.getCartByAccountId(accountId);
        Product product = productDao.getProductById(addCartRequest.getProductId());
        double priceToAdd = product.getPrice() * addCartRequest.getQuantity();
        double newTotalPrice = cart.getTotalPrice() + priceToAdd;
        if (!executeSQLHelper.executeStepsInTransaction(List.of(
                cartDao.addProductToCart(cart.getCartId(), product, addCartRequest.getQuantity()),
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
