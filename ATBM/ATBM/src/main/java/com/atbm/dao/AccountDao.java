package com.atbm.dao;

import com.atbm.utils.ExecuteSQLUtil;
import models.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements IDao<Account, Long> {

    // Thêm tài khoản mới
    @Override
    public boolean insert(Account account) {
        String query = "INSERT INTO Account (username, password, email) VALUES (?, ?, ?)";
        return ExecuteSQLUtil.executeUpdate(query, account.getUsername(), account.getPassword(), account.getEmail());
    }

    // Cập nhật thông tin tài khoản theo ID
    public boolean update(Account account) {
        String query = "UPDATE Account SET username = ?, password = ?, email = ? WHERE accountId = ?";
        return ExecuteSQLUtil.executeUpdate(query, account.getUsername(), account.getPassword(), account.getEmail(), account.getAccountId());
    }

    // Xóa tài khoản theo ID
    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Account WHERE accountId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    // Lấy tài khoản theo ID
    @Override
    public Account getById(Long id) {
        String query = "SELECT * FROM Account WHERE accountId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id);
        try {
            if (rs != null && rs.next()) {
                return new Account(
                    rs.getLong("accountId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tài khoản theo username
    public Account getByUsername(String username) {
        String query = "SELECT * FROM Account WHERE username = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, username);
        try {
            if (rs != null && rs.next()) {
                return new Account(
                    rs.getLong("accountId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả tài khoản
    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                accounts.add(new Account(
                    rs.getLong("accountId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Kiểm tra tài khoản có tồn tại không
    public boolean existsByUsername(String username) {
        String query = "SELECT 1 FROM Account WHERE username = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, username);
        try {
            return rs != null && rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account getAccountByEmail(String email) {
        String query = "SELECT * FROM Account WHERE email = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, email);
        try {
            if (rs != null && rs.next()) {
                return new Account(
                    rs.getLong("accountId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
