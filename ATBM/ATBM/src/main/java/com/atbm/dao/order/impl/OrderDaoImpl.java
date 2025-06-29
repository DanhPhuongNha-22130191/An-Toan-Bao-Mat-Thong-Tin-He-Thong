package com.atbm.dao.order.impl;

import com.atbm.dao.order.OrderDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Order;
import com.atbm.models.enums.OrderStatus;
import com.atbm.models.enums.PaymentMethod;
import com.atbm.models.wrapper.response.OrderResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderDaoImpl implements OrderDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public OrderDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }

    @Override
    public SQLTransactionStep<Long> insert(Order order) {
        List<String> fieldNames = List.of(ACCOUNT_ID, ORDER_SECURITY_ID, SHIPPING_INFO_ID, STATUS, TOTAL_PRICE, ORDER_AT, PAYMENT_METHOD);
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, fieldNames);
        try {
            return executeSQLHelper.buildInsertStepReturningId(query, order.getAccountId(), order.getOrderSecurityId(), order.getShippingInfoId(), OrderStatus.PROCESSING.name(), order.getTotalPrice(), Date.valueOf(LocalDate.now()), order.getPaymentMethod().name());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getOrdersByAccountId(long accountId) {
        String query = "SELECT * FROM Orders WHERE accountId = ?";
        List<Order> result = new ArrayList<>();
        try (ResultSet rs = executeSQLHelper.executeQuery(query, accountId)) {
            while (rs.next()) {
                result.add(createOrder(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Order getOrderById(long accountId, long orderId) {
        String query = "SELECT * FROM Orders WHERE accountId=? AND orderId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, accountId, orderId)) {
            if (rs.next())
                return createOrder(rs);
            else
                throw new RuntimeException("Không tìm thấy đơn hàng");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatus(long orderId, String status) {
        String query = "UPDATE Orders SET status=? WHERE  orderId=?";
        executeSQLHelper.executeUpdate(query, status, orderId);
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        String query = "SELECT * FROM Orders WHERE  status = ?";
        List<Order> result = new ArrayList<>();
        try (ResultSet rs = executeSQLHelper.executeQuery(query, status)) {
            while (rs.next()) {
                result.add(createOrder(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    @Override
    public List<Order> getOrders() {
        String query = "SELECT * FROM orders";
        List<Order> result = new ArrayList<>();
        try (ResultSet rs = executeSQLHelper.executeQuery(query)) {
            while (rs.next()) {
                result.add(createOrder(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    private Order createOrder(ResultSet resultSet) throws Exception {
        return new Order(
                resultSet.getLong(ORDER_ID),
                resultSet.getLong(ACCOUNT_ID),
                resultSet.getLong(ORDER_SECURITY_ID),
                resultSet.getLong(SHIPPING_INFO_ID),
                resultSet.getDouble(TOTAL_PRICE),
                OrderStatus.valueOf(resultSet.getString(STATUS)),
                PaymentMethod.valueOf(resultSet.getString(PAYMENT_METHOD)),
                resultSet.getTimestamp(ORDER_AT).toLocalDateTime()
        );
    }
}
