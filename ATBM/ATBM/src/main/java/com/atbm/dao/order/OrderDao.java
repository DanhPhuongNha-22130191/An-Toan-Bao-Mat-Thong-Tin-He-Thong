package com.atbm.dao.order;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.Order;

import java.util.List;

public interface OrderDao {
    SQLTransactionStep<Boolean> insert(Order order);
    List<Order> getOrdersByAccountId(long accountId);
    Order getOrderById(long orderId);
    void updateStatus(long accountId, long orderId, String status);
}
