package com.atbm.services;

import com.atbm.dao.OrderDao;
import com.atbm.dto.CartDTO;
import com.atbm.models.CartItem;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.models.OrderSecurity;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderService implements services.IService<Order, Long> {
    private OrderDao dao;

    public OrderService() {
        dao = new OrderDao();
    }

    @Override
    public boolean insert(Order entity) {
        if (dao.insert(entity)) {
            long id = getIdOrder(entity.getAccountId());
            return insertOrderDetail(entity.getOrderDetail(), id)
                    && insertOrderItems(getListCartItem(entity.getCartDTO(), id)) && insertOrerSecurity(entity.getOrderSecurity(), id);
        }
        return false;
    }

    public long getIdOrder(long accountId) {
        return dao.getIdOrder(accountId);
    }

    private boolean insertOrderDetail(OrderDetail detail, long orderId) {
        return dao.insertOrderDetail(detail, orderId);
    }

    private boolean insertOrerSecurity(OrderSecurity security, long orderId) {
        return dao.sign(orderId, security.getSignature(), security.getPublicKey());
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

    public List<CartItem> getListCartItem(CartDTO cartDTO, long orderId) {
        CartService cartService = new CartService();
        List<CartItem> cartItems = new LinkedList<CartItem>();
        for (CartDTO.CartItemDTO dto : cartDTO.getItems()) {
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


    public void sign(Long orderId, String signature, String publicKey) {
        dao.sign(orderId, signature, publicKey);
    }

    public OrderSecurity getSecurity(Long orderId) throws SQLException {
        return dao.getSecuriy(orderId);
    }

    public Long createOrder(Order order) {
        if (insert(order)) {
            return dao.getIdOrder(order.getAccountId());
        }
        return null;
    }
}
