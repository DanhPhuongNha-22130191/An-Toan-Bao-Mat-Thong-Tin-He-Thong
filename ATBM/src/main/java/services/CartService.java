package services;

import java.util.List;

import dao.CartDao;
import dto.CartDTO;
import dto.CartDTO.CartItemDTO;
import models.CartItem;
import models.Product;

public class CartService implements IService<CartItem, Long> {
	private CartDao dao;

	public CartService() {
		dao = new CartDao();
	}

	@Override
	public boolean insert(CartItem entity) {
		if (getById(entity.getCartItemId()) != null) {
			return update(entity);
		}
		return dao.insert(entity);
	}

	@Override
	public CartItem getById(Long id) {
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
			dto.add(product, cartItem.getCartItemId(), cartItem.getQuantity());
		}
		return dto;
	}

	public CartItem convertToModel(CartItemDTO dto) {
		return new CartItem(dto.getCartItemId(), dto.getQuantity());
	}

	public List<CartItem> getByAccId(long accountId) {
		return dao.getCartByAcc(accountId);
	}

	@Override
	public boolean update(CartItem cartItem) {
		if (cartItem.getQuantity() == 0)
			return delete(cartItem.getCartItemId());
		else
			return dao.update(cartItem);
	}

	@Override
	public boolean delete(Long id) {
		return dao.delete(id);

	}

}
