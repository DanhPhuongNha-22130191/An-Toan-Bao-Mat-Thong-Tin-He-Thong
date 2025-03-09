package services;

import java.util.List;

import dao.OrderDao;
import models.Order;

public class OrderService implements IService<Order, Long> {
	private OrderDao dao;

	public OrderService() {
		dao = new OrderDao();
	}

	@Override
	public boolean insert(Order entity) {
		return dao.insert(entity);
	}

	@Override
	public Order getById(Long id) {
		return dao.getById(id);
	}

	@Override
	public List<Order> getAll() {
		return dao.getAll();
	}

	@Override
	public boolean delete(Long id) {
		return dao.delete(id);
	}

	@Override
	public boolean update(Order entity) {
		return dao.update(entity);
	}

}
