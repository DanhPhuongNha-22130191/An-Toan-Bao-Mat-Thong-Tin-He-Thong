package com.atbm.dao.orderSecurity;

import com.atbm.models.entity.OrderSecurity;

public interface OrderSecurityDao {
    OrderSecurity getOrderSecurityById(long orderSecurityId);

    boolean insert(OrderSecurity orderSecurity);

    boolean update(OrderSecurity orderSecurity);

    boolean updateSignature(long orderSecurityId, String signature);
}
