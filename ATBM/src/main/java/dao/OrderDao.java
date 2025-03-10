package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import models.Order;
import utils.ExecuteSQLUtil;

public class OrderDao implements IDao<Order, Long> {

	@Override
	public boolean insert(Order entity) {
		String query = "insert into Order (accountId,shipping,paymentMethod,voucherId) values(?,?,?,?)";
		return ExecuteSQLUtil.executeUpdate(query, entity.getAccountId(), entity.getShipping(),
				entity.getPaymentMethod(), entity.getVoucherId());
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

}
