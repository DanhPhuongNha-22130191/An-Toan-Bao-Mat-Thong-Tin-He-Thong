package com.atbm.dao.account.impl;

import com.atbm.dao.account.AccountDao;
import com.atbm.models.entity.Account;
import com.atbm.models.enums.Role;
import com.atbm.utils.ExecuteSQLUtils;
import com.atbm.utils.LogUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao {
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String EMAIL = "email";
    private final String ROLES = "roles";
    private final String ACCOUNT_ID = "accountId";
    private final String PUBLIC_KEY = "publicKey";

    @Override
    public boolean insert(Account account) {
        String query = "INSERT INTO Account (username, password, email,roles) VALUES (?, ?, ?,?)";
        return ExecuteSQLUtils
                .executeUpdate(query,
                        account.getUsername(),
                        account.getPassword(),
                        account.getEmail(),
                        account.getRole().toString());
    }

    @Override
    public Account getAccountById(long accountId) {
        String query = "SELECT * FROM Account WHERE accountId = ?";
        try {
            return createAccount(ExecuteSQLUtils.ExecuteQuery(query, accountId));
        } catch (SQLException e) {
            LogUtils.debug(AccountDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Account lỗi");
        }
    }

    private Account createAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getLong(ACCOUNT_ID),
                resultSet.getString(USERNAME),
                resultSet.getString(PASSWORD),
                resultSet.getString(EMAIL),
                resultSet.getString(PUBLIC_KEY),
                Role.valueOf(resultSet.getString(ROLES))
        );
    }
}
