package com.atbm.dao;

import com.atbm.models.CartItem;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CartDao implements IDao<CartItem, Long> {

    private CartItem mapToCartItem(ResultSet rs) throws SQLException {
        return new CartItem(
                rs.getLong("cartItemId"),
                rs.getLong("accountId"),
                rs.getLong("productId"),
                rs.getLong("orderId") == 0 ? null : rs.getLong("orderId"), // Xử lý NULL
                rs.getInt("quantity")
        );
    }

    @Override
    public boolean insert(CartItem entity) {
        String query = "INSERT INTO CartItem (accountId, productId, quantity, orderId) VALUES (?, ?, ?, ?)";
        return ExecuteSQLUtil.executeUpdate(query,
                entity.getAccountId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getOrderId());
    }

    @Override
    public CartItem getById(Long id) {
        String query = "SELECT * FROM CartItem WHERE cartItemId = ?";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id)) {
            if (rs.next()) {
                return mapToCartItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CartItem> getByOrderId(Long orderId) {
        String query = "SELECT * FROM CartItem WHERE orderId = ?";
        return getCartItems(query, orderId);
    }

    @Override
    public List<CartItem> getAll() {
        String query = "SELECT * FROM CartItem";
        return getCartItems(query);
    }

    public List<CartItem> getCartByAcc(Long id) {
        String query = "SELECT * FROM CartItem WHERE accountId = ? AND orderId IS NULL";
        return getCartItems(query, id);
    }

    @Override
    public boolean update(CartItem entity) {
        String query = "UPDATE CartItem SET quantity = ?, orderId = ? WHERE cartItemId = ?";
        return ExecuteSQLUtil.executeUpdate(query,
                entity.getQuantity(),
                entity.getOrderId(),
                entity.getCartItemId());
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM CartItem WHERE cartItemId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    private List<CartItem> getCartItems(String query, Object... params) {
        List<CartItem> listCart = new LinkedList<>();
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, params)) {
            while (rs.next()) {
                listCart.add(mapToCartItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCart;
    }
}