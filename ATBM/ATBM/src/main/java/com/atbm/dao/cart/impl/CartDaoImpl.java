package com.atbm.dao.cart.impl;

import com.atbm.dao.cart.CartDao;
import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;
import com.atbm.utils.ExecuteSQLUtils;
import com.atbm.utils.LogUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CartDaoImpl implements CartDao {
    public static final String CART_TABLE_NAME = "Cart";

    public static final String CART_ID = "cartId";
    public static final String ACCOUNT_ID = "accountId";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String UPDATED_AT = "updateAt";
    public static final String CART_ITEM_TABLE_NAME = "CartItem";
    public static final String CART_ITEM_ID = "cartItemId";
    public static final String PRODUCT_ID = "productId";
    public static final String QUANTITY = "quantity";
    public static final String PRICE_SNAPSHOT = "priceSnapshot";
    public static final String NAME_SNAPSHOT = "nameSnapshot";
    public static final String IMAGE_SNAPSHOT = "imageSnapshot";

    @Override
    public void insert(Cart cart) {
        List<String> fieldNames = List.of(ACCOUNT_ID, TOTAL_PRICE, UPDATED_AT);
        String query = ExecuteSQLUtils.createInsertQuery(CART_TABLE_NAME, fieldNames);
        ExecuteSQLUtils.executeUpdate(query, cart.getAccountId(), cart.getTotalPrice(), Date.valueOf(cart.getUpdateAt()));
    }

    @Override
    public long getCartIdByAccountId(long accountId) {
        String query = "SELECT cartId FROM Cart WHERE accountId = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, accountId)) {
            return rs.getLong(CART_ID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart getCartByAccountId(long accountId) {
        String query = "SELECT * FROM Cart WHERE accountId = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, accountId)) {
            return createCart(rs);
        } catch (Exception e) {
            throw new RuntimeException("Có lỗi xảy ra không lấy giỏ hàng thành công");
        }
    }

    @Override
    public List<CartItem> getCartItemsByCartId(long cartId) {
        List<CartItem> result = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE cartId = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, cartId)) {
            while (rs.next()) {
                result.add(createCartItem(rs));
            }
        } catch (Exception e) {
            LogUtils.debug(CartDaoImpl.class, e.getMessage());
            throw new RuntimeException("Có lỗi xảy ra không lấy giỏ hàng thành công");
        }
        return result;
    }


    @Override
    public void updateQuantity(long accountId, long cartItemId, int newQuantity) {
        // Đồng bộ lại dữ liệu với database để tránh tình trạng dữ liệu không nhất quán
        Cart cart = getCartByAccountId(accountId);
        CartItem targetItem = findCartItemById(cartItemId);
        int oldQuantity = targetItem.getQuantity();
        boolean increase = newQuantity > oldQuantity;

        int quantityDelta = Math.abs(newQuantity - oldQuantity);
        double priceDelta = quantityDelta * targetItem.getPriceSnapshot();
        double newTotalPrice = increase
                ? cart.getTotalPrice() + priceDelta
                : cart.getTotalPrice() - priceDelta;

        String query = ExecuteSQLUtils.createUpdateQuery(CART_ITEM_TABLE_NAME, List.of(QUANTITY), List.of(CART_ITEM_ID, CART_ID));
        ExecuteSQLUtils.executeUpdateInTransaction(List.of(
                connection ->
                        ExecuteSQLUtils.executeUpdate(connection, query, newQuantity, cartItemId, cart.getCartId()),
                connection ->
                        updateTotalPrice(accountId, newTotalPrice).apply(connection)
        ));
    }

    @Override
    public void addProductToCart(long accountId, Product product, int quantity) {
        long cartId = getCartIdByAccountId(accountId);
        String query = ExecuteSQLUtils.createInsertQuery(CART_ITEM_TABLE_NAME, List.of(PRODUCT_ID, CART_ID, QUANTITY, PRICE_SNAPSHOT, NAME_SNAPSHOT, IMAGE_SNAPSHOT));
        ExecuteSQLUtils.executeUpdateInTransaction(List.of(
                connection ->
                        ExecuteSQLUtils.executeUpdate(connection, query, product.getProductId(), cartId, quantity,
                                product.getPrice(), product.getName(), product.getImage()),
                connection ->
                        updateTotalPrice(accountId, product.getPrice()).apply(connection)
        ));
    }

    @Override
    public void removeProductFromCart(long accountId, long cartItemId) {
        // Đồng bộ lại dữ liệu với database để tránh tình trạng dữ liệu không nhất quán
        Cart cart = getCartByAccountId(accountId);
        CartItem targetItem = findCartItemById(cartItemId);

        double productAmount = targetItem.getPriceSnapshot() * targetItem.getQuantity();
        double newTotalPrice = cart.getTotalPrice() - productAmount;

        String query = "DELETE FROM CartItem WHERE cartId=? AND cartItemId=?";
        ExecuteSQLUtils.executeUpdateInTransaction(List.of(
                connection ->
                        ExecuteSQLUtils.executeUpdate(connection, query, cart.getCartId(), cartItemId),
                connection ->
                        updateTotalPrice(accountId, newTotalPrice).apply(connection)
        ));
    }

    @Override
    public void clearCart(long accountId) {
        long cartId = getCartIdByAccountId(accountId);
        String deleteCartItem = "DELETE FROM CartItem WHERE cartId=?";
        ExecuteSQLUtils.executeUpdateInTransaction(List.of(
                connection ->
                        ExecuteSQLUtils.executeUpdate(connection, deleteCartItem, cartId)
                ,
                connection ->
                        updateTotalPrice(accountId, 0).apply(connection)
        ));
    }


    private Function<Connection, Boolean> updateTotalPrice(long accountId, double totalPrice) {
        String query = ExecuteSQLUtils.createUpdateQuery(CART_TABLE_NAME, List.of(TOTAL_PRICE, UPDATED_AT), List.of(ACCOUNT_ID));
        return con -> ExecuteSQLUtils.executeUpdate(con, query, totalPrice, Date.valueOf(java.time.LocalDate.now()), accountId);
    }

    private CartItem findCartItemById(long cartItemId) {
        String query = "SELECT * FROM cartItem WHERE cartItemId = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, cartItemId)) {
            return createCartItem(rs);
        } catch (Exception e) {
            LogUtils.debug(CartDaoImpl.class, e.getMessage());
            throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
    }

    private Cart createCart(ResultSet rs) throws Exception {
        return new Cart(rs.getLong(CART_ID),
                rs.getLong(ACCOUNT_ID),
                rs.getDouble(TOTAL_PRICE),
                rs.getDate(UPDATED_AT).toLocalDate()
        );
    }

    private CartItem createCartItem(ResultSet rs) throws Exception {
        return new CartItem(rs.getLong(CART_ITEM_ID),
                rs.getLong(CART_ID),
                rs.getLong(PRODUCT_ID),
                rs.getInt(QUANTITY),
                rs.getDouble(PRICE_SNAPSHOT),
                rs.getNString(NAME_SNAPSHOT),
                rs.getBytes(IMAGE_SNAPSHOT)
        );
    }


}
