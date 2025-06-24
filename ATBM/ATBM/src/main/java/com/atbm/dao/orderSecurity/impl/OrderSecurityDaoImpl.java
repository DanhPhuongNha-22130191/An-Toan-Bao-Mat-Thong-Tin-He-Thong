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
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public OrderSecurityDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }

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
    public void updateSignature(long orderSecurityId, String signature) {
        String query = "UPDATE OrderSecurity SET signature=? WHERE orderSecurityId=?";
        if(executeSQLHelper.executeUpdate(query, signature, orderSecurityId))
            throw new RuntimeException("Cập nhật thất bại");
    }

    @Override
    public boolean isDigitallySigned(long orderSecurityId) {
        String query = "SELECT COUNT(*) FROM OrderSecurity WHERE orderSecurityId=? and signature IS NOT NULL";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, orderSecurityId)) {
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OrderSecurity createOrderSecurity(ResultSet resultSet) throws SQLException {
        return new OrderSecurity(
                resultSet.getLong(ORDER_SECURITY_ID),
                resultSet.getString(PUBLIC_KEY),
                resultSet.getString(SIGNATURE)
        );
    }
}
