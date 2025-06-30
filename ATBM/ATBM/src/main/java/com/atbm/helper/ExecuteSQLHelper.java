package com.atbm.helper;


import com.atbm.database.DBConnection;
import com.atbm.database.SQLTransactionStep;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author minhhien
 */
@ApplicationScoped
public class ExecuteSQLHelper {
    private final DBConnection dbConnection;

    @Inject
    public ExecuteSQLHelper(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public ExecuteSQLHelper() {
        dbConnection = null;
    }

    public String createInsertQuery(String tableName, List<String> fieldNames) {
        String columns = String.join(", ", fieldNames);
        String placeholders = String.join(", ", fieldNames.stream().map(f -> "?").toArray(String[]::new));
        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    public String createUpdateQuery(String tableName, List<String> updateFields, List<String> conditionFields) {
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
    public boolean executeUpdate(String query, Object... data) {
        Connection connect = dbConnection.getConnection();
        try {
            PreparedStatement preStatement = connect.prepareStatement(query);
            for (int i = 0; i < data.length; i++)
                if (data[i] != null)
                    preStatement.setObject(i + 1, data[i]);
                else preStatement.setNull(i + 1,Types.VARCHAR);
            return preStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LogUtils.debug(ExecuteSQLHelper.class, e.getMessage());
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
    public ResultSet executeQuery(String query, Object... data) {
        Connection connect = dbConnection.getConnection();
        try {
            PreparedStatement preStatement = connect.prepareStatement(query);
            for (int i = 0; i < data.length; i++)
                preStatement.setObject(i + 1, data[i]);
            return preStatement.executeQuery();
        } catch (SQLException e) {
            LogUtils.debug(ExecuteSQLHelper.class, e.getMessage());
            throw new RuntimeException("Lỗi khi lấy dữ liệu");
        }
    }

    /**
     * Phương thức thực hiện lệnh update, insert, delete
     *
     * @param query lệnh query
     * @return true nếu thành công, ngược lại false
     */
    public SQLTransactionStep<Boolean> buildUpdateStep(String query, Object... params) {
        return connection -> {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param == null) {
                        stmt.setNull(i + 1, Types.VARBINARY);
                    } else if (param instanceof byte[]) {
                        stmt.setBytes(i + 1, (byte[]) param);
                    } else if (param instanceof String) {
                        stmt.setString(i + 1, (String) param);
                    } else if (param instanceof Long) {
                        stmt.setLong(i + 1, (Long) param);
                    } else if (param instanceof Integer) {
                        stmt.setInt(i + 1, (Integer) param);
                    } else if (param instanceof Double) {
                        stmt.setDouble(i + 1, (Double) param);
                    } else {
                        stmt.setObject(i + 1, param);
                    }
                }
                return stmt.executeUpdate() > 0 ? Boolean.TRUE : null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi thực hiện thay đổi: " + e.getMessage());
            }
        };
    }

    /**
     * Phương thức thực hiện lệnh update, insert, delete trong 1 transaction
     *
     * @param query lệnh query
     * @return generateId, nếu lỗi hoặc không update thàng công thì tả về null
     */
    public SQLTransactionStep<Long> buildInsertStepReturningId(String query, Object... params) {
        return connection -> {
            try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
                return stmt.executeUpdate() > 0 ?
                        (stmt.getGeneratedKeys().next() ? stmt.getGeneratedKeys().getLong(1) : null)
                        : null;
            } catch (SQLException e) {
                e.printStackTrace();
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
    public boolean executeStepsInTransaction(List<SQLTransactionStep<?>> steps) {
        Connection conn = dbConnection.getConnection();
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
            e.printStackTrace();
            LogUtils.debug(ExecuteSQLHelper.class, e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed: " + ex.getMessage());
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage());
        }
    }
}
