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
        String query = "SELECT * FROM OrderSecurity WHERE orderSecurityId=?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, orderSecurityId)) {
            if (rs.next()) {
                return createOrderSecurity(rs);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy OrderSecurity: " + e.getMessage());
        }
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
    public void updateSignature(long orderSecurityId, String publicKey, String signature) {
        String query = executeSQLHelper.createUpdateQuery(TABLE_NAME, List.of(PUBLIC_KEY, SIGNATURE), List.of(ORDER_SECURITY_ID));
        if (!executeSQLHelper.executeUpdate(query, publicKey, signature, orderSecurityId)) {
            throw new RuntimeException("Cập nhật chữ ký thất bại");
        }
    }

    @Override
    public boolean isDigitallySigned(long orderSecurityId) {
        String query = "SELECT COUNT(*) as count FROM OrderSecurity WHERE orderSecurityId=? AND signature IS NOT NULL";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, orderSecurityId)) {
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi kiểm tra chữ ký số: " + e.getMessage());
        }
    }

    @Override
    public void clearInformation(long orderSecurityId) {
        String query = executeSQLHelper.createUpdateQuery(TABLE_NAME, List.of(PUBLIC_KEY, SIGNATURE), List.of(ORDER_SECURITY_ID));
        executeSQLHelper.executeUpdate(query, null, null, orderSecurityId);
    }

    private OrderSecurity createOrderSecurity(ResultSet resultSet) throws SQLException {
        return new OrderSecurity(
                resultSet.getLong(ORDER_SECURITY_ID),
                resultSet.getString(PUBLIC_KEY),
                resultSet.getString(SIGNATURE)
        );
    }
}
