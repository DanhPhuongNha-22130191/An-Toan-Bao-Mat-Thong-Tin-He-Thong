package com.atbm.services;

import com.atbm.dao.CartDao;
import com.atbm.dto.CartDTO;
import com.atbm.models.CartItem;
import com.atbm.models.Product;

import java.util.List;
import java.util.logging.Logger;

public class CartService implements IService<CartItem, Long> {
    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    private CartDao dao;

    public CartService() {
        dao = new CartDao();
    }

    @Override
    public boolean insert(CartItem entity) {
        if (getById(entity.getCartItemId()) != null) {
            return update(entity);
        }
        LOGGER.info("Inserting new CartItem: accountId=" + entity.getAccountId() + ", productId=" + entity.getProductId());
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
        LOGGER.info("Converting CartDTO for accountId: " + accountId);
        CartDTO dto = new CartDTO();
        List<CartItem> cartItems = getByAccId(accountId);
        ProductService productService = new ProductService();
        if (cartItems == null || cartItems.isEmpty()) {
            LOGGER.warning("No CartItems found for accountId: " + accountId);
            return dto;
        }
        for (CartItem cartItem : cartItems) {
            Product product = productService.getById(cartItem.getProductId());
            if (product == null) {
                LOGGER.warning("Product not found for productId: " + cartItem.getProductId());
                continue;
            }
            if (product.getPrice() <= 0) {
                LOGGER.warning("Invalid product price for productId: " + cartItem.getProductId() + ", price: " + product.getPrice());
                continue;
            }
            try {
                dto.add(product, cartItem.getCartItemId(),accountId, cartItem.getQuantity());
                LOGGER.info("Added CartItem: productId=" + cartItem.getProductId() + ", quantity=" + cartItem.getQuantity() + ", price=" + product.getPrice());
            } catch (IllegalStateException e) {
                LOGGER.warning("Failed to add CartItem for productId: " + cartItem.getProductId() + ": " + e.getMessage());
            }
        }
        LOGGER.info("CartDTO items count: " + dto.getItems().size() + ", subtotal: " + dto.getSubTotal());
        return dto;
    }

    public CartDTO convertToDTO(Long orderId) {
        LOGGER.info("Converting CartDTO for orderId: " + orderId);
        CartDTO dto = new CartDTO();
        ProductService productService = new ProductService();
        List<CartItem> cartItems = getCartByOrderId(orderId);
        if (cartItems == null || cartItems.isEmpty()) {
            LOGGER.warning("No CartItems found for orderId: " + orderId);
            return dto;
        }
        LOGGER.info("Found " + cartItems.size() + " CartItems for orderId: " + orderId);
        for (CartItem cartItem : cartItems) {
            Product product = productService.getById(cartItem.getProductId());
            if (product == null) {
                LOGGER.warning("Product not found for productId: " + cartItem.getProductId() + " in orderId: " + orderId);
                continue;
            }
            if (product.getPrice() <= 0) {
                LOGGER.warning("Invalid product price for productId: " + cartItem.getProductId() + ", price: " + product.getPrice());
                continue;
            }
            try {
                dto.add(product, cartItem.getCartItemId(),cartItem.getAccountId(), cartItem.getQuantity());
                LOGGER.info("Added CartItem: productId=" + cartItem.getProductId() + ", quantity=" + cartItem.getQuantity() + ", price=" + product.getPrice());
            } catch (IllegalStateException e) {
                LOGGER.warning("Failed to add CartItem for productId: " + cartItem.getProductId() + " in orderId: " + orderId + ": " + e.getMessage());
            }
        }
        LOGGER.info("CartDTO items count: " + dto.getItems().size() + ", subtotal: " + dto.getSubTotal());
        return dto;
    }

    public List<CartItem> getCartByOrderId(Long orderId) {
        return dao.getByOrderId(orderId);
    }

    public CartItem convertToModel(CartDTO.CartItemDTO dto) {
        return new CartItem(dto.getCartItemId(), dto.getQuantity());
    }

    public List<CartItem> getByAccId(long accountId) {
        return dao.getCartByAcc(accountId);
    }

    @Override
    public boolean update(CartItem cartItem) {
        if (cartItem.getQuantity() == 0) {
            return delete(cartItem.getCartItemId());
        }
        LOGGER.info("Updating CartItem: cartItemId=" + cartItem.getCartItemId() + ", quantity=" + cartItem.getQuantity());
        return dao.update(cartItem);
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("Deleting CartItem: cartItemId=" + id);
        return dao.delete(id);
    }

    public boolean addToCart(long accountId, long productId, int quantity) {
        LOGGER.info("Adding to cart: accountId=" + accountId + ", productId=" + productId + ", quantity=" + quantity);
        List<CartItem> existingItems = dao.getCartByAcc(accountId);
        CartItem existingItem = existingItems.stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return update(existingItem);
        } else {
            CartItem newItem = new CartItem(0, accountId, productId, null, quantity);
            return insert(newItem);
        }
    }
}