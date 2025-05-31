package com.atbm.dao;

import com.atbm.models.Account;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AccountDao implements IDao<Account, Long> {
    private static final Logger LOGGER = Logger.getLogger(AccountDao.class.getName());

    // Thêm tài khoản mới
    @Override
    public boolean insert(Account account) {
        String query = "INSERT INTO Account (username, password, email, publicKeyActive) VALUES (?, ?, ?, ?)";
        try {
            return ExecuteSQLUtil.executeUpdate(query, account.getUsername(), account.getPassword(),
                    account.getEmail(), account.getPublicKeyActive());
        } catch (Exception e) {
            LOGGER.severe("Error inserting account: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin tài khoản theo ID
    @Override
    public boolean update(Account account) {
        String query = "UPDATE Account SET username = ?, password = ?, email = ?, publicKeyActive = ? WHERE accountId = ?";
        try {
            return ExecuteSQLUtil.executeUpdate(query, account.getUsername(), account.getPassword(),
                    account.getEmail(), account.getPublicKeyActive(), account.getAccountId());
        } catch (Exception e) {
            LOGGER.severe("Error updating account with ID " + account.getAccountId() + ": " + e.getMessage());
            return false;
        }
    }

    // Xóa tài khoản theo ID
    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Account WHERE accountId = ?";
        try {
            return ExecuteSQLUtil.executeUpdate(query, id);
        } catch (Exception e) {
            LOGGER.severe("Error deleting account with ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    // Lấy tài khoản theo ID
    @Override
    public Account getById(Long id) {
        String query = "SELECT * FROM Account WHERE accountId = ?";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id)) {
            if (rs != null && rs.next()) {
                return new Account(
                        rs.getLong("accountId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("publicKeyActive")
                );
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving account by ID " + id + ": " + e.getMessage());
        }
        return null;
    }

    // Lấy tài khoản theo username
    public Account getByUsername(String username) {
        String query = "SELECT * FROM Account WHERE username = ?";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, username)) {
            if (rs != null && rs.next()) {
                return new Account(
                        rs.getLong("accountId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("publicKeyActive")
                );
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving account by username " + username + ": " + e.getMessage());
        }
        return null;
    }

    // Lấy tất cả tài khoản
    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query)) {
            while (rs != null && rs.next()) {
                accounts.add(new Account(
                        rs.getLong("accountId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("publicKeyActive")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving all accounts: " + e.getMessage());
        }
        return accounts;
    }

    // Kiểm tra username đã tồn tại
    public boolean existsByUsername(String username) {
        String query = "SELECT 1 FROM Account WHERE username = ?";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, username)) {
            return rs != null && rs.next();
        } catch (SQLException e) {
            LOGGER.severe("Error checking username existence for " + username + ": " + e.getMessage());
            return false;
        }
    }

    // Lấy tài khoản theo email
    public Account getAccountByEmail(String email) {
        String query = "SELECT * FROM Account WHERE email = ?";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, email)) {
            if (rs != null && rs.next()) {
                return new Account(
                        rs.getLong("accountId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("publicKeyActive")
                );
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving account by email " + email + ": " + e.getMessage());
        }
        return null;
    }

    // Lấy publicKeyActive theo accountId
    public String getPublicKeyIsActive(Long accountId) {
        String query = "SELECT publicKeyActive FROM Account WHERE accountId = ?";
        try (ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, accountId)) {
            if (rs != null && rs.next()) {
                return rs.getString("publicKeyActive");
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving publicKeyActive for account ID " + accountId + ": " + e.getMessage());
        }
        return null;
    }

    // Test method
    public static void main(String[] args) {
        try {
            List<Account> accounts = new AccountDao().getAll();
            System.out.println("Accounts retrieved: " + accounts.size());
        } catch (Exception e) {
            LOGGER.severe("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}