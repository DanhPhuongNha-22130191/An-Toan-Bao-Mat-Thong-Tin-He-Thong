package com.atbm.dao.account.impl;

import com.atbm.dao.account.AccountDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Account;
import com.atbm.models.entity.Product;
import com.atbm.models.enums.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccountDaoImpl implements AccountDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public AccountDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper =  executeSQLHelper;
    }

    @Override
    public SQLTransactionStep<Long> insert(Account account) {
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(USERNAME, PASSWORD, EMAIL, ROLES, PUBLIC_KEY_ACTIVE, IS_DELETED));
        try {
            return executeSQLHelper.buildInsertStepReturningId(query,
                    account.getUsername(),
                    account.getPassword(),
                    account.getEmail(),
                    account.getRole().toString(),
                    account.getPublicKeyActive(),
                    account.isDeleted());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm tài khoản: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Account account) {
        String query= executeSQLHelper.createUpdateQuery(TABLE_NAME, List.of(USERNAME, PASSWORD, EMAIL, ROLES, PUBLIC_KEY_ACTIVE, IS_DELETED), List.of(ACCOUNT_ID));
        try {
            return executeSQLHelper.executeUpdate(query,
                    account.getUsername(),
                    account.getPassword(),
                    account.getEmail(),
                    account.getRole().toString(),
                    account.getPublicKeyActive(),
                    account.isDeleted(),
                    account.getAccountId());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật tài khoản: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(long accountId) {
        String query = "UPDATE Account SET isDeleted = 1 WHERE accountId = ?";
        try {
            return executeSQLHelper.executeUpdate(query, accountId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa tài khoản: " + e.getMessage(), e);
        }
    }


    @Override
    public Account getAccountById(long accountId) {
        String query = "SELECT * FROM Account WHERE accountId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, accountId)) {
            if (rs != null && rs.next()) {
                return createAccount(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy tài khoản theo ID: " + e.getMessage());
        }
    }

    @Override
    public Account getAccountByUsername(String username) {
        String query = "SELECT * FROM Account WHERE username = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, username)) {
            if (rs != null && rs.next()) {
                return createAccount(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy tài khoản theo username: " + e.getMessage());
        }
    }

    @Override
    public Account getAccountByEmail(String email) {
        String query = "SELECT * FROM Account WHERE email = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, email)) {
            if (rs != null && rs.next()) {
                return createAccount(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy tài khoản theo email: " + e.getMessage());
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        String query = "SELECT 1 FROM Account WHERE username = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, username)) {
            return rs != null && rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra username: " + e.getMessage());
        }
    }

    @Override
    public String getPublicKeyActive(long accountId) {
        String query = "SELECT publicKeyActive FROM Account WHERE accountId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, accountId)) {
            if (rs != null && rs.next()) {
                return rs.getString("publicKeyActive");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy publicKeyActive: " + e.getMessage());
        }
    }

    @Override
    public List<Account> getAccounts() {
        String query = "SELECT * FROM Account WHERE isDeleted = 0";
        List<Account> accounts = new ArrayList<>();
        try (ResultSet rs = executeSQLHelper.executeQuery(query)) {
            while (rs.next()) {
                Account account = createAccount(rs);
                if (account != null) {
                    accounts.add(account);
                }
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException("Lấy Account lỗi", e);
        }
    }

    private Account createAccount(ResultSet rs) throws SQLException {
        return new Account(
                rs.getLong(ACCOUNT_ID),
                rs.getString(USERNAME),
                rs.getString(PASSWORD),
                rs.getString(EMAIL),
                rs.getString(PUBLIC_KEY_ACTIVE),
                Role.valueOf(rs.getString(ROLES)),
                rs.getBoolean(IS_DELETED)
        );
    }
}