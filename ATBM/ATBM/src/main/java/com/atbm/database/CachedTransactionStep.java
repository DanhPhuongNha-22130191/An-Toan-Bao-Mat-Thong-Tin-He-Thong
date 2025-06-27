package com.atbm.database;

import java.sql.Connection;
import java.sql.SQLException;

public class CachedTransactionStep<T> implements SQLTransactionStep<T> {
    private T result;
    private boolean executed;
    private final SQLTransactionStep<T> transactionStep;

    public CachedTransactionStep(SQLTransactionStep<T> transactionStep) {
        this.transactionStep = transactionStep;
    }

    @Override
    public T apply(Connection connection) throws SQLException {
        if (!executed) {
            result = transactionStep.apply(connection);
            executed = true;
        }
        return result;
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public T getResult() {
        return result;
    }
}
