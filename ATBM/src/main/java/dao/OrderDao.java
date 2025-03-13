package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import models.CartItem;
import models.Order;
import models.OrderDetail;
import utils.ExecuteSQLUtil;

public class OrderDao implements IDao<Order, Long> {

	@Override
	public boolean insert(Order entity) {
		String query = "insert into Order (accountId,shipping,paymentMethod,voucherId) values(?,?,?,?)";
		return ExecuteSQLUtil.executeUpdate(query, entity.getAccountId(), entity.getShipping(),
				entity.getPaymentMethod(), entity.getVoucherId());
	}

	public long getIdOrder(long accountId) {
		String query = "select orderId from Order where accountId=?";
		ResultSet resultSet = ExecuteSQLUtil.executeQuery(query, accountId);
		long newOrderId = 0;
		try {
			while (resultSet.next())
				newOrderId = resultSet.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newOrderId;
	}

	@Override
	public Order getById(Long id) {
		String query = "select * from Order where orderId=?";
		ResultSet resultSet = ExecuteSQLUtil.executeQuery(query, id);
		Order order = null;
		try {
			if (resultSet.next()) {
				order = new Order(resultSet.getLong(1), resultSet.getLong(2), resultSet.getDouble(3),
						resultSet.getString(4), resultSet.getLong(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> getAll() {
		String query = "select * from Order";
		ResultSet resultSet = ExecuteSQLUtil.executeQuery(query, new Object[0]);
		List<Order> listOrder = new LinkedList<Order>();
		try {
			while (resultSet.next()) {
				Order order = new Order(resultSet.getLong(1), resultSet.getLong(2), resultSet.getDouble(3),
						resultSet.getString(4), resultSet.getLong(5));
				listOrder.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			listOrder = null;
		}
		return listOrder;
	}

	@Override
	public boolean delete(Long id) {
		String query = "delete  from order where orderId=?";
		return ExecuteSQLUtil.executeUpdate(query, id);
	}

	@Override
	public boolean update(Order entity) {
		return false;
	}

	public boolean insertOrderDetail(OrderDetail detail, long orderId) {
		String query = "insert into OrderDetail (orderId,fullName,phone,email,address,orderNote) values(?,?,?,?,?,?)";
		return ExecuteSQLUtil.executeUpdate(query, orderId, detail.getFullName(), detail.getPhone(), detail.getEmail(),
				detail.getAddress(), detail.getOrderNote());
	}

	public boolean insertOrderItem(CartItem cartItems) {
		String query = "update CartItem set orderId=? where cartItemId=?";
		return ExecuteSQLUtil.executeUpdate(query, cartItems.getOrderId(), cartItems.getCartItemId());
	}

	public void updateProduct(long productId, int quantity) {
		String query = "update Product set stock=? where productId=?";
		ExecuteSQLUtil.executeUpdate(query, quantity, productId);

	}

}
