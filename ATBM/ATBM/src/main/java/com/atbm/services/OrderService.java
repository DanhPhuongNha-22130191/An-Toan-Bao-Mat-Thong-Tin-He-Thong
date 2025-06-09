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
import java.util.logging.Logger;

public class OrderService implements IService<Order, Long> {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());
    private OrderDao dao;

    public OrderService() {
        dao = new OrderDao();
    }

    @Override
    public boolean insert(Order entity) {
        if (dao.insert(entity)) {
            long id = getOrderId(entity.getAccountId());
            return insertOrderDetail(entity.getOrderDetail(), id)
                    && insertOrderItems(getListCartItem(entity.getCartDTO(), id))
                    && insertOrerSecurity(entity.getOrderSecurity(), id);
        }
        return false;
    }

    public long getOrderId(long accountId) {
        return dao.getOrderId(accountId);
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


    public List<Order> getAllByAccountId(long accountId) {
        List<Order> orders = dao.getAllById(accountId);
        for (Order order : orders) {
            order.setCartDTO(new CartService().convertToDTO(order.getOrderId()));
        }
        return orders;
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
            return dao.getOrderId(order.getAccountId());
        }
        return null;
    }
    public boolean updateStatus(long orderId, String status) {
        return dao.updateStatus(orderId, status);
    }


    public List<Order> getOrdersByAccountId(long accountId) {
        List<Order> orders = dao.getOrdersByAccountId(accountId);
        LOGGER.info("OrderService: Retrieved " + (orders != null ? orders.size() : 0) + " orders for accountId " + accountId);
        if (orders != null) {
            for (Order order : orders) {
                LOGGER.info("OrderService: Processing Order ID: " + order.getOrderId() + ", Account ID: " + order.getAccountId());
                // Gán CartDTO
                try {
                    CartService cartService = new CartService();
                    CartDTO cartDTO = cartService.convertToDTO(order.getOrderId());
                    if (cartDTO != null) {
                        order.setCartDTO(cartDTO);
                        LOGGER.info("OrderService: Successfully set CartDTO for Order ID: " + order.getOrderId());
                    } else {
                        LOGGER.warning("OrderService: CartDTO is null for Order ID: " + order.getOrderId());
                    }
                } catch (Exception e) {
                    LOGGER.severe("OrderService: Error setting CartDTO for Order ID " + order.getOrderId() + ": " + e.getMessage());
                }
                // Gán OrderDetail
                try {
                    OrderDetail orderDetail = dao.getOrderDetailByOrderId(order.getOrderId());
                    if (orderDetail != null) {
                        order.setOrderDetail(orderDetail);
                        LOGGER.info("OrderService: Successfully set OrderDetail for Order ID: " + order.getOrderId() + ", FullName: " + orderDetail.getFullName());
                    } else {
                        LOGGER.warning("OrderService: OrderDetail is null for Order ID: " + order.getOrderId());
                    }
                } catch (Exception e) {
                    LOGGER.severe("OrderService: Error setting OrderDetail for Order ID " + order.getOrderId() + ": " + e.getMessage());
                }
            }
        }
        return orders;
    }
    public boolean hasOrders(long userId) {
        return OrderDao.countOrdersByUserId(userId) > 0;
    }
}
