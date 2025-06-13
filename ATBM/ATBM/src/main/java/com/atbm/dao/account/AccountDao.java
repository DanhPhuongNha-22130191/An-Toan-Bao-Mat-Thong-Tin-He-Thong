package com.atbm.dao.account;

import com.atbm.models.entity.Account;

public interface AccountDao {
    boolean insert(Account account);
    Account getAccountById(long accountId);
}
