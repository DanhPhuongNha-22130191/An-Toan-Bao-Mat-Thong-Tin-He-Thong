package com.atbm.services;

import com.atbm.dao.account.AccountDao;
import com.atbm.dao.account.impl.AccountDaoImpl;
import com.atbm.models.entity.Account;
import com.atbm.models.wrapper.request.RegisterRequest;
import com.atbm.models.wrapper.response.AccountResponse;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpl();
    }

    public boolean register(RegisterRequest registerRequest) {
        Account account = new Account(registerRequest.username(), registerRequest.password(), registerRequest.email());
        return accountDao.insert(account);
    }

    public AccountResponse getAccountById(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        return new AccountResponse(
                account.getAccountId(),
                account.getUsername(),
                account.getEmail(),
                account.getPublicKey(),
                account.getRole());
    }
}
