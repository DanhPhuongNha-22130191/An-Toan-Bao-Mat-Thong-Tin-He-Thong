package services;

import java.util.List;

import dao.CartDao;
import dto.CartDTO;
import models.CartItem;
import models.Product;

public class CartService implements IService<CartItem, Long[]> {
	private CartDao dao;

	public CartService() {
		dao = new CartDao();
	}

	@Override
	public boolean insert(CartItem entity) {
		return dao.insert(entity);
	}

	@Override
	public CartItem getById(Long[] id) {
		return dao.getById(id);
	}

	@Override
	public List<CartItem> getAll() {
		return dao.getAll();
	}

	public CartDTO convertToDTO(long accountId) {
		CartDTO dto = new CartDTO();
		List<CartItem> cartItems = getByAccId(accountId);
		ProductService productService = new ProductService();
		for (CartItem cartItem : cartItems) {
			Product product = productService.getById(cartItem.getProductId());
			dto.add(product, cartItem.getQuantity());
		}
		return dto;
	}
	public List<CartItem> getByAccId(long accountId){
		return dao.getCartByAcc(accountId);
	}

	public boolean updateCart(CartItem cartItem) {
		Long[] id = new Long[] {  cartItem.getAccountId(), cartItem.getProductId() };
		if (cartItem.getQuantity() == 0)
			return delete(id);
		else
			return dao.updateCart(id, cartItem.getQuantity());
	}

	@Override
	public boolean delete(Long[] id) {
		return dao.delete(id);

	}

}
