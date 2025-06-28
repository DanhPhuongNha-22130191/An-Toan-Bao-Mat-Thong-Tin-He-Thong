package com.atbm.dao.orderItem;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.OrderItem;

import java.util.List;

public interface OrderItemDao {
    String TABLE_NAME = "OrderItem";
    String ORDER_ITEM_ID = "orderItemId";
    String ORDER_ID = "orderId";
    String PRODUCT_ID = "productId";
    String QUANTITY = "quantity";
    String PRICE = "priceSnapshot";
    String NAME = "nameSnapshot";
    String IMG = "imageSnapshot";
    SQLTransactionStep<Boolean> insert(OrderItem orderItem);
    List<OrderItem> getOrderItemsByOrderId(long orderId);
}
