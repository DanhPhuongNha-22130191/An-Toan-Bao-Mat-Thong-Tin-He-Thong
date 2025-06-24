package com.atbm.dao.orderSecurity;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.OrderSecurity;

public interface OrderSecurityDao {
    OrderSecurity getOrderSecurityById(long orderSecurityId);

    SQLTransactionStep<Long> insert(OrderSecurity orderSecurity);

    SQLTransactionStep<Boolean> update(OrderSecurity orderSecurity);

    void updateSignature(long orderSecurityId, String signature);

    boolean isDigitallySigned(long orderSecurityId);
}
