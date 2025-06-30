package com.atbm.dao.cart.impl;

import com.atbm.dao.cart.CartDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Cart;
import com.atbm.models.entity.CartItem;
import com.atbm.models.entity.Product;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CartDaoImpl implements CartDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public CartDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }

    @Override
    public void insert(Cart cart) {
        List<String> fieldNames = List.of(ACCOUNT_ID, TOTAL_PRICE, UPDATED_AT);
        String query = executeSQLHelper.createInsertQuery(CART_TABLE_NAME, fieldNames);
        executeSQLHelper.executeUpdate(query, cart.getAccountId(), cart.getTotalPrice(), Date.valueOf(cart.getUpdateAt()));
    }

    @Override
    public long getCartIdByAccountId(long accountId) {
        String query = "SELECT cartId FROM Cart WHERE accountId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, accountId)) {
            if (!rs.next()) throw new RuntimeException("Không tìm thấy mã giỏ hàng");
            return rs.getLong(CART_ID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart getCartByAccountId(long accountId) {
        String query = "SELECT * FROM Cart WHERE accountId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, accountId)) {
            if (!rs.next()) throw new RuntimeException("Không tìm thấy giỏ hàng");
            return createCart(rs);
        } catch (Exception e) {
            LogUtils.debug(CartDaoImpl.class, e.getMessage());
            throw new RuntimeException("Có lỗi xảy ra, lấy giỏ hàng không thành công");
        }
    }

    @Override
    public List<CartItem> getCartItemsByCartId(long cartId) {
        List<CartItem> result = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE cartId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, cartId)) {
            while (rs.next()) {
                result.add(createCartItem(rs));
            }
        } catch (Exception e) {
            LogUtils.debug(CartDaoImpl.class, e.getMessage());
            throw new RuntimeException("Có lỗi xảy ra lấy giỏ hàng không thành công");
        }
        return result;
    }


    @Override
    public SQLTransactionStep<Boolean> updateQuantity(long cartId, long cartItemId, int newQuantity) {
        String query = executeSQLHelper.createUpdateQuery(CART_ITEM_TABLE_NAME, List.of(QUANTITY), List.of(CART_ITEM_ID, CART_ID));
        return executeSQLHelper.buildUpdateStep(query, newQuantity, cartItemId, cartId);
    }

    @Override
    public SQLTransactionStep<Boolean> addProductToCart(long cartId, Product product, int quantity) {
        String query = executeSQLHelper.createInsertQuery(CART_ITEM_TABLE_NAME, List.of(PRODUCT_ID, CART_ID, QUANTITY, PRICE_SNAPSHOT, NAME_SNAPSHOT, IMAGE_SNAPSHOT));
        return executeSQLHelper.buildUpdateStep(query, product.getProductId(), cartId, quantity, product.getPrice(), product.getName(), product.getImage());
    }

    @Override
    public SQLTransactionStep<Boolean> removeProductFromCart(long cartId, long cartItemId) {
        String query = "DELETE FROM CartItem WHERE cartId=? AND cartItemId=?";
        return executeSQLHelper.buildUpdateStep(query, cartId, cartItemId);
    }

    @Override
    public SQLTransactionStep<Boolean> clearCart(long cartId) {
        String deleteCartItem = "DELETE FROM CartItem WHERE cartId=?";
        return executeSQLHelper.buildUpdateStep(deleteCartItem, cartId);
    }

    @Override
    public SQLTransactionStep<Boolean> updateTotalPrice(long accountId, double newTotalPrice) {
        String query = executeSQLHelper.createUpdateQuery(CART_TABLE_NAME, List.of(TOTAL_PRICE, UPDATED_AT), List.of(ACCOUNT_ID));
        return executeSQLHelper.buildUpdateStep(query, newTotalPrice, Date.valueOf(java.time.LocalDate.now()), accountId);
    }

    @Override
    public CartItem getCartItemById(long cartItemId) {
        System.out.println(cartItemId);
        String query = "SELECT * FROM CartItem WHERE cartItemId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, cartItemId)) {
            if (!rs.next()) throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
            return createCartItem(rs);
        } catch (Exception e) {
            LogUtils.debug(CartDaoImpl.class, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Cart createCart(ResultSet rs) throws Exception {
        return new Cart(rs.getLong(CART_ID), rs.getLong(ACCOUNT_ID), rs.getDouble(TOTAL_PRICE), rs.getDate(UPDATED_AT).toLocalDate());
    }

    private CartItem createCartItem(ResultSet rs) throws Exception {
        return new CartItem(rs.getLong(CART_ITEM_ID), rs.getLong(CART_ID), rs.getLong(PRODUCT_ID), rs.getInt(QUANTITY), rs.getDouble(PRICE_SNAPSHOT), rs.getString(NAME_SNAPSHOT), rs.getBytes(IMAGE_SNAPSHOT));
    }


}
