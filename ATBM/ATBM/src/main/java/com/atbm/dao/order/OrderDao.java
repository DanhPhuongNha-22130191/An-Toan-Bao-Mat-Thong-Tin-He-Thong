package com.atbm.dao.order;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.Order;
import com.atbm.models.wrapper.response.OrderResponse;

import java.util.List;

public interface OrderDao {
     String TABLE_NAME = "Orders";
     String ORDER_ID = "orderId";
     String ACCOUNT_ID = "accountId";
     String STATUS = "status";
     String TOTAL_PRICE = "totalPrice";
     String ORDER_AT = "orderAt";
     String PAYMENT_METHOD = "paymentMethod";
     String SHIPPING_INFO_ID = "shippingInfoId";
     String ORDER_SECURITY_ID = "orderSecurityId";

    SQLTransactionStep<Long> insert(Order order);

    List<Order> getOrdersByAccountId(long accountId);

    Order getOrderById(long accountId, long orderId);

    void updateStatus( long orderId, String status);

    List<Order> getOrdersByStatus(String status);
}
