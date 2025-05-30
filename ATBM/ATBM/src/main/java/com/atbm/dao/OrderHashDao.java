package com.atbm.dao;

import com.atbm.models.OrderHash;
import com.atbm.utils.ExecuteSQLUtil;
import java.sql.ResultSet;

public class OrderHashDao {
    public boolean insert(OrderHash orderHash) {
        String query = "INSERT INTO OrderHash (orderId, hash) VALUES (?, ?)";
        return ExecuteSQLUtil.executeUpdate(query, orderHash.getOrderId(), orderHash.getHash());
    }

    public OrderHash getByOrderId(long orderId) {
        String query = "SELECT * FROM OrderHash WHERE orderId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, orderId);
        try {
            if (rs.next()) {
                return new OrderHash(rs.getLong("orderId"), rs.getString("hash"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}