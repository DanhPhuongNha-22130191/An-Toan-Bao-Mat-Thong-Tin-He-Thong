package com.atbm.dao.orderSecurity;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.OrderSecurity;

public interface OrderSecurityDao {
    String TABLE_NAME = "OrderSecurity";
    String ORDER_SECURITY_ID = "orderSecurityId";
    String PUBLIC_KEY = "publicKey";
    String SIGNATURE = "signature";

    OrderSecurity getOrderSecurityById(long orderSecurityId);

    SQLTransactionStep<Long> insert(OrderSecurity orderSecurity);

    SQLTransactionStep<Boolean> update(OrderSecurity orderSecurity);

    void updateSignature(long orderSecurityId, String signature);

    boolean isDigitallySigned(long orderSecurityId);
}
