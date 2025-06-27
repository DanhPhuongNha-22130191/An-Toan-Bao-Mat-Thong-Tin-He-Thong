package com.atbm.dao.account;

import com.atbm.models.entity.Account;

import java.util.List;

public interface AccountDao {
    String USERNAME = "username";
    String PASSWORD = "password";
    String EMAIL = "email";
    String ROLES = "role";
    String ACCOUNT_ID = "accountId";
    String PUBLIC_KEY_ACTIVE = "publicKeyActive";
    String IS_DELETED = "isDeleted";

    boolean insert(Account account);

    boolean update(Account account);

    boolean delete(long accountId);

    Account getAccountById(long accountId);

    Account getAccountByUsername(String username);

    Account getAccountByEmail(String email);

    boolean existsByUsername(String username);

    String getPublicKeyActive(long accountId);

    List<Account> getAccounts();
}