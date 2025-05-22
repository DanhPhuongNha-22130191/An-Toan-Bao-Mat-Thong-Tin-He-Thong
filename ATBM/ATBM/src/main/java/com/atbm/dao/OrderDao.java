package com.atbm.dao;

import com.atbm.dto.CartDTO;
import com.atbm.models.CartItem;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderDao implements IDao<Order, Long> {

    @Override
    public boolean insert(Order entity) {
        String query = "insert into Orders (accountId,shipping,paymentMethod,voucherId) values(?,?,?,?)";
        return ExecuteSQLUtil.executeUpdate(query, entity.getAccountId(), entity.getShipping(),
                entity.getPaymentMethod(), entity.getVoucherId());
    }

    public long getIdOrder(long accountId) {
        String query = "select orderId from Orders where accountId=?";
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
        String query = "select * from Orders where orderId=?";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query, id);
        Order order = null;
        try {
            if (resultSet.next()) {
                order = new Order(resultSet.getLong(1), resultSet.getLong(2), resultSet.getDouble(3),
                        resultSet.getString(4), resultSet.getLong(5));
                order.setOrderDetail(getDetailById(order.getOrderId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public OrderDetail getDetailById(long orderId) throws SQLException {
        String query = "select * from OrderDetails where orderId=?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, orderId);
        OrderDetail orderDetail = null;
        if (rs.next()) {
            orderDetail = new OrderDetail();
            orderDetail.setOrderDetailId(rs.getLong("order_detail_id"));
            orderDetail.setOrderId(rs.getLong("order_id"));
            orderDetail.setFullName(rs.getString("full_name"));
            orderDetail.setPhone(rs.getString("phone"));
            orderDetail.setEmail(rs.getString("email"));
            orderDetail.setAddress(rs.getString("address"));
            orderDetail.setOrderNote(rs.getString("order_note"));
        }
        return orderDetail;

    }

    @Override
    public List<Order> getAll() {
        String query = "select * from Orders";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query);
        List<Order> listOrder = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order(resultSet.getLong(1), resultSet.getLong(2), resultSet.getDouble(3),
                        resultSet.getString(4), resultSet.getLong(5));
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
        String query = "delete  from Orders where orderId=?";
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

}
