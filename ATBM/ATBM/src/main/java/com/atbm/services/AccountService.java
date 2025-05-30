package com.atbm.services;

import com.atbm.dao.AccountDao;
import com.atbm.dto.AccountDTO;
import com.atbm.models.Account;
import com.atbm.utils.HashUtil;
import java.util.List;

public class AccountService implements IService<Account, Long> {
    private AccountDao accountDao = new AccountDao();

    @Override
    public boolean insert(Account account) {
        return accountDao.insert(account);
    }

    @Override
    public Account getById(Long accountId) {
        return accountDao.getById(accountId);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public boolean delete(Long accountId) {
        return accountDao.delete(accountId);
    }

    @Override
    public boolean update(Account account) {
        return accountDao.update(account);
    }

    // Đăng ký tài khoản mới
    public boolean register(String username, String password, String email) {
        if (accountDao.getByUsername(username) != null) {
            return false; // Username đã tồn tại
        }
        Account newAccount = new Account(0, username, password, email, null);
        return insert(newAccount);
    }

    // Đăng nhập
    public Account login(String username, String password) {
        Account account = accountDao.getByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

 // Kiểm tra mật khẩu cũ có đúng không
    public boolean checkPassword(long accountId, String oldPassword) {
        Account account = accountDao.getById(accountId);
        if (account != null) {
            return account.getPassword().equals(oldPassword);
        }
        return false;
    }

    // Cập nhật mật khẩu mới
    public boolean updatePassword(long accountId, String newPassword) {
        Account account = accountDao.getById(accountId);
        if (account != null) {
            account.setPassword(newPassword);
            return accountDao.update(account);
        }
        return false;
    }

    // Cập nhật thông tin cá nhân
    public boolean updateProfile(AccountDTO user) {
        Account account = accountDao.getById(user.getAccountId());
        if (account != null) {
            account.setUsername(user.getUsername());
            account.setEmail(user.getEmail());
            // Giữ nguyên mật khẩu cũ
            return accountDao.update(account);
        }
        return false;
    }

    // Cập nhật thông tin tài khoản (không còn sử dụng, thay thế bằng updateProfile và updatePassword)
    public boolean updateAccount(Long accountId, String newUsername, String newPassword, String newEmail) {
        Account existingAccount = getById(accountId);
        if (existingAccount == null) {
            return false; // Không tìm thấy tài khoản
        }
        Account updatedAccount = new Account(accountId, newUsername, newPassword, newEmail);
        return update(updatedAccount);
    }

    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email);
    }


    public String getPublicKeyIsActive(Long accountId) {
        return accountDao.getPublicKeyIsActive(accountId);
    }
}
    public String generateApiKey(long accountId) {
        Account account = accountDao.getById(accountId);
        if (account != null) {
            String apiKey = HashUtil.generateApiKey();
            account.setApiKey(apiKey);
            update(account);
            return apiKey;
        }
        return null;
    }

    public void revokeApiKey(long accountId) {
        Account account = accountDao.getById(accountId);
        if (account != null) {
            account.setApiKey(null);
            update(account);
        }
    }
}