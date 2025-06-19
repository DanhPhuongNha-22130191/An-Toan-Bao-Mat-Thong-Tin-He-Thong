package com.atbm.dao.account;

import com.atbm.models.entity.Account;

public interface AccountDao {
    boolean insert(Account account);
    boolean update(Account account);
    boolean softDelete(long accountId); // Thêm phương thức để vô hiệu hóa tài khoản
    Account getAccountById(long accountId);
    Account getAccountByUsername(String username);
    Account getAccountByEmail(String email);
    boolean existsByUsername(String username);
    String getPublicKeyActive(long accountId);
}