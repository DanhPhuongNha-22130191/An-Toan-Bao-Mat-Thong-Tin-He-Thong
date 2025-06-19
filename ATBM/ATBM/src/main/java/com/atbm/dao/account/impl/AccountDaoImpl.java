package com.atbm.dao.account.impl;

import com.atbm.dao.account.AccountDao;
import com.atbm.models.entity.Account;
import com.atbm.models.enums.Role;
import com.atbm.utils.ExecuteSQLUtils;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLES = "roles";
    private static final String ACCOUNT_ID = "accountId";
    private static final String PUBLIC_KEY_ACTIVE = "publicKeyActive";
    private static final String IS_DELETE = "isDelete";

    @Override
    public boolean insert(Account account) {
        String query = "INSERT INTO Account (username, password, email, roles, publicKeyActive, isDelete) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            return ExecuteSQLUtils.executeUpdate(query,
                    account.getUsername(),
                    account.getPassword(),
                    account.getEmail(),
                    account.getRole().toString(),
                    account.getPublicKeyActive(),
                    account.isDelete());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm tài khoản: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Account account) {
        String query = "UPDATE Account SET username = ?, password = ?, email = ?, roles = ?, publicKeyActive = ?, isDelete = ? WHERE accountId = ?";
        try {
            return ExecuteSQLUtils.executeUpdate(query,
                    account.getUsername(),
                    account.getPassword(),
                    account.getEmail(),
                    account.getRole().toString(),
                    account.getPublicKeyActive(),
                    account.isDelete(),
                    account.getAccountId());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật tài khoản: " + e.getMessage());
        }
    }

    @Override
    public boolean softDelete(long accountId) {
        String query = "UPDATE Account SET isDelete = ? WHERE accountId = ?";
        try {
            return ExecuteSQLUtils.executeUpdate(query, true, accountId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi vô hiệu hóa tài khoản: " + e.getMessage());
        }
    }

    @Override
    public Account getAccountById(long accountId) {
        String query = "SELECT * FROM Account WHERE accountId = ? AND isDelete = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, accountId, false)) {
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
        String query = "SELECT * FROM Account WHERE username = ? AND isDelete = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, username, false)) {
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
        String query = "SELECT * FROM Account WHERE email = ? AND isDelete = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, email, false)) {
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
        String query = "SELECT 1 FROM Account WHERE username = ? AND isDelete = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, username, false)) {
            return rs != null && rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi kiểm tra username: " + e.getMessage());
        }
    }

    @Override
    public String getPublicKeyActive(long accountId) {
        String query = "SELECT publicKeyActive FROM Account WHERE accountId = ? AND isDelete = ?";
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query, accountId, false)) {
            if (rs != null && rs.next()) {
                return rs.getString("publicKeyActive");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy publicKeyActive: " + e.getMessage());
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
                rs.getBoolean(IS_DELETE)
        );
    }
}