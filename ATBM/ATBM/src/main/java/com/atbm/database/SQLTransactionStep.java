package com.atbm.database;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLTransactionStep<T> {

    T apply(Connection connection) throws SQLException;

    default boolean isExecuted() {
        return true;
    }

    default T getResult() {
        throw new UnsupportedOperationException("Chưa thực thi chưa thể trả kết quả");
    }
}
