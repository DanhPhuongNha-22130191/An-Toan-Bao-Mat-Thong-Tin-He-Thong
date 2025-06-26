package com.atbm.dao.orderItem.impl;

import com.atbm.dao.orderItem.OrderItemDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.util.List;

@ApplicationScoped
public class OrderItemDaoImpl implements OrderItemDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public OrderItemDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }


    @Override
    public SQLTransactionStep<Boolean> insert(OrderItem orderItem) {
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(ORDER_ID, PRODUCT_ID, QUANTITY, PRICE, NAME, IMG));
        return executeSQLHelper.buildUpdateStep(query, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPriceSnapshot(), orderItem.getNameSnapshot(), orderItem.getImageSnapshot());
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(long orderId) {
        String query = "SELECT * FROM OrderItems WHERE orderId = ?";
        List<OrderItem> result = new java.util.ArrayList<>();
        try (ResultSet rs = executeSQLHelper.executeQuery(query, orderId)) {
            while (rs.next()) {
                result.add(createOrderItem(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private OrderItem createOrderItem(ResultSet resultSet) throws Exception {
        return new OrderItem(
                resultSet.getLong(ORDER_ITEM_ID),
                resultSet.getLong(ORDER_ID),
                resultSet.getLong(PRODUCT_ID),
                resultSet.getInt(QUANTITY),
                resultSet.getNString(NAME),
                resultSet.getDouble(PRICE),
                resultSet.getBytes(IMG)
        );
    }
}
