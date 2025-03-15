package services;

import java.util.LinkedList;
import java.util.List;

import dao.OrderDao;
import dto.CartDTO;
import dto.CartDTO.CartItemDTO;
import models.CartItem;
import models.Order;
import models.OrderDetail;

public class OrderService implements IService<Order, Long> {
	private OrderDao dao;

	public OrderService() {
		dao = new OrderDao();
	}

	@Override
	public boolean insert(Order entity) {
		if (dao.insert(entity)) {
			long id = getIdOrder(entity.getAccountId());
			return insertOrderDetail(entity.getOrderDetail(), id)
					&& insertOrderItems(getListCartItem(entity.getCartDTO(), id));
		}
		return false;
	}
	public long getIdOrder(long accountId) {
		return dao.getIdOrder(accountId);
	}

	private boolean insertOrderDetail(OrderDetail detail, long orderId) {
		return dao.insertOrderDetail(detail, orderId);
	}

	private void updateProduct(long productId, int quantity) {
		dao.updateProduct(productId, quantity);
	}

	private boolean insertOrderItems(List<CartItem> items) {
		for (CartItem cartItem : items) {
			if (!dao.insertOrderItem(cartItem)) {
				return false;
			}
			updateProduct(cartItem.getProductId(), cartItem.getQuantity());
		}
		return true;
	}

	private List<CartItem> getListCartItem(CartDTO cartDTO, long orderId) {
		CartService cartService = new CartService();
		List<CartItem> cartItems = new LinkedList<CartItem>();
		for (CartItemDTO dto : cartDTO.getItems()) {
			CartItem item = cartService.convertToModel(dto);
			item.setOrderId(orderId);
			cartItems.add(item);
		}
		return cartItems;

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
