package services;

import java.util.LinkedList;
import java.util.List;

import dao.CartDao;
import dto.CartDTO.CartItemDTO;
import models.CartItem;

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

	public List<CartItemDTO> convertToDTO() {
		List<CartItem> cartItems = getAll();
		List<CartItemDTO> listDTO = new LinkedList<>();
//		ProductService productService = new ProductService();
//		for (CartItem cartItem : cartItems) {
//			Product product = productService.getById(cartItem.getProductId());
//			listDTO.add(new CartItemDTO(product.getProductId(), product.getProductName(), product.getProductImg(),
//					product.getProductPrice(), cartItem.getQuantity()));
//		}
		return listDTO;
	}

	@Override
	public boolean delete(Long[] id) {
		return dao.delete(id);

	}

}
