package com.atbm.utils;


import com.atbm.database.DBConnection;
import com.atbm.database.SQLTransactionStep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author minhhien
 */
public class ExecuteSQLUtils {
    public static String createInsertQuery(String tableName, List<String> fieldNames) {
        String columns = String.join(", ", fieldNames);
        String placeholders = String.join(", ", fieldNames.stream().map(f -> "?").toArray(String[]::new));
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    public static String createUpdateQuery(String tableName, List<String> updateFields, List<String> conditionFields) {
        String setClause = String.join(", ", updateFields.stream().map(f -> f + "=?").toArray(String[]::new));
        String whereClause = String.join(" AND ", conditionFields.stream().map(f -> f + "=?").toArray(String[]::new));
        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
    }

    /**
     * Phương thức thực hiện lệnh update, insert, delete
     *
     * @param query lệnh query
     * @param data  tham số
     * @return true nếu thành công, ngược lại false
     */
    public static boolean executeUpdate(String query, Object... data) {
        Connection connect = DBConnection.getConnection();
        try {
            PreparedStatement preStatement = connect.prepareStatement(query);
            for (int i = 0; i < data.length; i++)
                preStatement.setObject(i + 1, data[i]);
            return preStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LogUtils.debug(ExecuteSQLUtils.class, e.getMessage());
            throw new RuntimeException("Lỗi khi thực hiện thay đổi");
        }
    }

    /**
     * Phương thức thực hiện lệnh excuteQuery
     *
     * @param query lệnh query
     * @param data  tham số
     * @return ResultSet đại diện cho từng dòng giá trị
     */
    public static ResultSet executeQuery(String query, Object... data) {
        Connection connect = DBConnection.getConnection();
        try {
            PreparedStatement preStatement = connect.prepareStatement(query);
            for (int i = 0; i < data.length; i++)
                preStatement.setObject(i + 1, data[i]);
            return preStatement.executeQuery();
        } catch (SQLException e) {
            LogUtils.debug(ExecuteSQLUtils.class, e.getMessage());
            throw new RuntimeException("Lỗi khi lấy dữ liệu");
        }
    }

    /**
     * Phương thức thực hiện lệnh update, insert, delete
     *
     * @param query lệnh query
     * @return true nếu thành công, ngược lại false
     */
    public static SQLTransactionStep<Boolean> buildUpdateStep(String query, Object... params) {
        return connection -> {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Lỗi khi thực hiện thay đổi");
            }
        };
    }

    /**
     * Phương thức thực hiện một danh sách các bước thao tác với cơ sở dữ liệu trong cùng một giao dịch (transaction).
     * Nếu bất kỳ bước nào thất bại (trả về null hoặc ném exception), toàn bộ transaction sẽ bị rollback.
     *
     * @param steps Danh sách các bước thao tác với kiểu SQLTransactionStep<?> (có thể trả về giá trị)
     * @return true nếu tất cả các bước thành công và transaction được commit
     */
    public static boolean executeStepsInTransaction(List<SQLTransactionStep<?>> steps) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            for (SQLTransactionStep<?> step : steps) {
                Object result = step.apply(conn);
                if (result == null) {
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            LogUtils.debug(ExecuteSQLUtils.class, e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed: " + ex.getMessage());
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage());
        }
    }
}
