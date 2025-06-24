package com.atbm.dao.orderSecurity.impl;

import com.atbm.dao.orderSecurity.OrderSecurityDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.OrderSecurity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class OrderSecurityDaoImpl implements OrderSecurityDao {
    @Inject
    private ExecuteSQLHelper executeSQLHelper;
    private static final String TABLE_NAME = "OrderSecurity";
    private static final String ORDER_SECURITY_ID = "orderSecurityId";
    private static final String PUBLIC_KEY = "publicKey";
    private static final String SIGNATURE = "signature";

    @Override
    public OrderSecurity getOrderSecurityById(long orderSecurityId) {
        return null;
    }

    @Override
    public SQLTransactionStep<Long> insert(OrderSecurity orderSecurity) {
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(PUBLIC_KEY, SIGNATURE));
        return executeSQLHelper.buildInsertStepReturningId(query, orderSecurity.getPublicKey(), orderSecurity.getSignature());
    }

    @Override
    public SQLTransactionStep<Boolean> update(OrderSecurity orderSecurity) {
        String query = executeSQLHelper.createUpdateQuery(TABLE_NAME, List.of(PUBLIC_KEY, SIGNATURE), List.of(ORDER_SECURITY_ID));
        return executeSQLHelper.buildUpdateStep(query, orderSecurity.getPublicKey(), orderSecurity.getSignature(), orderSecurity.getOrderSecurityId());
    }

    @Override
    public SQLTransactionStep<Boolean> updateSignature(long orderSecurityId, String signature) {
        String query = "UPDATE OrderSecurity SET signature=? WHERE orderSecurityId=?";
        return executeSQLHelper.buildUpdateStep(query, signature, orderSecurityId);
    }

    private OrderSecurity createOrderSecurity(ResultSet resultSet) throws SQLException {
        return new OrderSecurity(
                resultSet.getLong(ORDER_SECURITY_ID),
                resultSet.getString(PUBLIC_KEY),
                resultSet.getString(SIGNATURE)
        );
    }
}
