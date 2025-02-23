package dao;

import java.util.LinkedList;
import java.util.List;

import models.CartItem;

/**
 * @author minhhien
 * Dùng để kết nối với db để query liên quan tới cart
 */
public class CartDao implements IDao<CartItem, Long[]>{

	@Override
	public boolean insert(CartItem entity) {
		return false;
	}

	@Override
	public CartItem getById(Long[] id) {
		return null;
	}

	@Override
	public List<CartItem> getAll() {
		List<CartItem> listCart = new LinkedList<CartItem>();
		listCart.add(new CartItem(0, 0, 0));
		return listCart;
	}

	@Override
	public boolean delete(Long[] id) {
		return false;
	}
	

}
