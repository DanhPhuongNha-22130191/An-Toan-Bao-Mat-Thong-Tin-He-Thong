package com.atbm.dao;

import com.atbm.models.CartItem;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.models.OrderSecurity;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.atbm.database.DBConnection.getConnection;

public class OrderDao implements IDao<Order, Long> {

    @Override
    public boolean insert(Order entity) {
        String query = "insert into [Order] (accountId,shipping,paymentMethod,voucherId,status,orderDate) values(?,?,?,?,?,?)";
        return ExecuteSQLUtil.executeUpdate(query, entity.getAccountId(), entity.getShipping(),
                entity.getPaymentMethod(), entity.getVoucherId(),entity.getStatus(),entity.getOrderDate());
    }

    public long getIdOrder(long accountId) {
        String query = "select orderId from [Order] where accountId=?";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query, accountId);
        long newOrderId = 0;
        try {
            while (resultSet.next())
                newOrderId = resultSet.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newOrderId;
    }

    @Override
    public Order getById(Long id) {
        String query = "select * from [Order] where orderId=?";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query, id);
        Order order = null;
        try {
            if (resultSet.next()) {
                order = new Order(resultSet.getLong("orderId"), resultSet.getLong("accountId"), resultSet.getDouble("shipping"),
                        resultSet.getString("paymentMethod"), resultSet.getLong("voucherId"));
                order.setOrderDate(resultSet.getDate("orderDate"));
                order.setStatus(resultSet.getString("status"));
                order.setOrderDetail(getDetailById(order.getOrderId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public OrderDetail getDetailById(long orderId) throws SQLException {
        String query = "select * from OrderDetail where orderId=?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, orderId);
        OrderDetail orderDetail = null;
        if (rs.next()) {
            orderDetail = new OrderDetail();
            orderDetail.setOrderDetailId(rs.getLong("orderDetailId"));
            orderDetail.setOrderId(rs.getLong("orderId"));
            orderDetail.setFullName(rs.getString("fullName"));
            orderDetail.setPhone(rs.getString("phone"));
            orderDetail.setEmail(rs.getString("email"));
            orderDetail.setAddress(rs.getString("address"));
            orderDetail.setOrderNote(rs.getString("orderNote"));
        }
        return orderDetail;

    }

    @Override
    public List<Order> getAll() {
        String query = "select * from [Order]";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query);
        List<Order> listOrder = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Long voucherId = resultSet.getObject("voucherId", Long.class); // ✅ An toàn với NULL

                Order order = new Order(
                        resultSet.getLong("orderId"),
                        resultSet.getLong("accountId"),
                        resultSet.getDouble("shipping"),
                        resultSet.getString("paymentMethod"),
                        voucherId
                );

                order.setOrderDate(resultSet.getDate("orderDate"));
                order.setStatus(resultSet.getString("status"));
                order.setOrderDetail(getDetailById(order.getOrderId()));
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
        String query = "delete  from [Order] where orderId=?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }

    public boolean insertOrderDetail(OrderDetail detail, long orderId) {
        String query = "insert into OrderDetail (orderId,fullName,phone,email,address,orderNote) values(?,?,?,?,?,?)";
        return ExecuteSQLUtil.executeUpdate(query, orderId, detail.getFullName(), detail.getPhone(), detail.getEmail(),
                detail.getAddress(), detail.getOrderNote());
    }

    public boolean insertOrderItem(CartItem cartItems) {
        String query = "update CartItem set orderId=? where cartItemId=?";
        return ExecuteSQLUtil.executeUpdate(query, cartItems.getOrderId(), cartItems.getCartItemId());
    }

    public void updateProduct(long productId, int quantity) {
        String query = "update Product set stock=? where productId=?";
        ExecuteSQLUtil.executeUpdate(query, quantity, productId);

    }

    public boolean sign(Long orderId, String signature,String publicKey) {
        String query = "insert into OrderSecurity (orderId,signature,publicKey) values (?,?,?)";
        return ExecuteSQLUtil.executeUpdate(query,orderId, signature ,publicKey);
    }

    public OrderSecurity getSecuriy(Long orderId) throws SQLException {
        String query = "select * from OrderSecurity where orderId=?";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query, orderId);
        OrderSecurity orderSecurity = null;
        if(resultSet.next()){
            orderSecurity = new OrderSecurity();
            orderSecurity.setOrderId(orderId);
            orderSecurity.setSignature(resultSet.getString(2));
            orderSecurity.setSignature(resultSet.getString(3));
        }
        return orderSecurity;
    }

    public List<Order> getOrdersByAccountId(long accountId) {
        String query = "SELECT * FROM [Order] WHERE accountId = ?";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query, accountId);
        List<Order> orders = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong("orderId"));
                order.setAccountId(resultSet.getLong("accountId"));
                order.setShipping(resultSet.getDouble("shipping"));
                order.setPaymentMethod(resultSet.getString("paymentMethod"));
                // Nếu cột voucherId trong DB cho phép NULL, bạn có thể dùng getObject để tránh lỡ parse NULL thành 0
                long v = resultSet.getLong("voucherId");
                if (!resultSet.wasNull()) {
                    order.setVoucherId(v);
                }
                // Gán status (kiểu NVARCHAR hoặc VARCHAR trong DB)
                order.setStatus(resultSet.getString("status"));
                // Gán orderDate (kiểu Date hoặc DateTime)
                order.setOrderDate(resultSet.getDate("orderDate"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    public OrderDetail getOrderDetailByOrderId(long orderId) {
        String query = "SELECT * FROM OrderDetail WHERE orderId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderDetailId(rs.getLong("orderDetailId"));
                detail.setOrderId(rs.getLong("orderId"));
                detail.setFullName(rs.getString("fullName"));
                detail.setPhone(rs.getString("phone"));
                detail.setEmail(rs.getString("email"));
                detail.setAddress(rs.getString("address"));
                detail.setOrderNote(rs.getString("orderNote"));

                return detail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
