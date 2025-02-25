package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import models.CartItem;
import utils.ExecuteSQLUtil;

/**
 * @author minhhien Dùng để kết nối với db để query liên quan tới cart
 */
public class CartDao implements IDao<CartItem, Long[]> {

	@Override
	public boolean insert(CartItem entity) {
		String query = "insert from CartItem (accountId,productId,quantity) values (?,?,?)";
		return ExecuteSQLUtil.executeUpdate(query, entity.getAccountId(), entity.getProductId(), entity.getQuantity());
	}

	@Override
	public CartItem getById(Long[] id) {
		String query = "select * from CartItem where accountId=? and productId=?";
		ResultSet resultSet = ExecuteSQLUtil.ExcuteQuery(query, id[0], id[1]);
		CartItem cartItem = null;
		try {
			if (resultSet.next()) {
				cartItem = new CartItem(resultSet.getLong(1), resultSet.getLong(2), resultSet.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartItem;
	}

	@Override
	public List<CartItem> getAll() {
		String query = "select * from CartItem";
		ResultSet resultSet = ExecuteSQLUtil.ExcuteQuery(query, new Object[0]);
		List<CartItem> listCart = new LinkedList<CartItem>();
		try {
			while (resultSet.next()) {
				CartItem cartItem = new CartItem(resultSet.getLong(1), resultSet.getLong(2), resultSet.getInt(3));
				listCart.add(cartItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			listCart = null;
		}
		return listCart;
	}

	public List<CartItem> getCartByAcc(Long id) {
		String query = "select * from CartItem where accountId=?";
		ResultSet resultSet = ExecuteSQLUtil.ExcuteQuery(query, id);
		List<CartItem> listCart = new LinkedList<CartItem>();
		try {
			while (resultSet.next()) {
				CartItem cartItem = new CartItem(resultSet.getLong(1), resultSet.getLong(2), resultSet.getInt(3));
				listCart.add(cartItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			listCart = null;
		}
		return listCart;
	}

	@Override
	public boolean delete(Long[] id) {
		String query = "delete from CartItem where accountId=? and productId=?";
		return ExecuteSQLUtil.executeUpdate(query, id[0], id[1]);
	}

}
