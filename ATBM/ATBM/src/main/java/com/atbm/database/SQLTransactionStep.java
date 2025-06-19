package com.atbm.database;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLTransactionStep<T> {
    T apply(Connection connection) throws SQLException;
}
