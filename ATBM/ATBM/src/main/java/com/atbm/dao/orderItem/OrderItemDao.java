package com.atbm.dao.orderItem;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {
    SQLTransactionStep<Boolean> insert(OrderItem orderItem);
    List<OrderItem> getOrderItemsByOrderId(long orderId);
}
