package com.atbm.utils;


import com.atbm.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
     * Hàm thực hiện update trong 1 transaction
     */
    public static boolean executeUpdate(Connection conn, String query, Object... params) {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
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
     * Phương thức hỗ thực hiện nhóm lệnh update trong 1 transaction
     * @param actions các function update được tạo ra bằng hàm @see {@link ExecuteSQLUtils#executeUpdate(Connection, String, Object[])}
     */
    public static void executeUpdateInTransaction(List<Function<Connection, Boolean>> actions) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            boolean result = actions.stream().allMatch(action -> action.apply(conn));
            if (result) {
                conn.commit();
            } else {
                conn.rollback();
                throw new RuntimeException("Lỗi khi cập nhật dữ liệu");
            }
        } catch (SQLException e) {
            LogUtils.debug(ExecuteSQLUtils.class, e.getMessage());
            throw new RuntimeException("Lỗi khi cập nhật dữ liệu");
        }
    }
}
