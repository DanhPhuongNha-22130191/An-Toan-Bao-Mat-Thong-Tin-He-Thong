package com.atbm.services;

import com.atbm.dao.account.AccountDao;
import com.atbm.dao.account.impl.AccountDaoImpl;
import com.atbm.models.entity.Account;
import com.atbm.models.wrapper.request.RegisterRequest;
import com.atbm.models.wrapper.request.LoginRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.utils.SignatureUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpl();
    }

    public boolean register(RegisterRequest registerRequest) throws NoSuchAlgorithmException {
        if (accountDao.existsByUsername(registerRequest.username().trim())) {
            return false;
        }
        String hashedPassword = SignatureUtil.hash(registerRequest.password());
        Account account = new Account(
                registerRequest.username().trim(),
                hashedPassword,
                registerRequest.email().trim()
        );
        return accountDao.insert(account);
    }

    public AccountResponse login(LoginRequest loginRequest) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountByUsername(loginRequest.username().trim());
        if (account != null && SignatureUtil.hash(loginRequest.password()).equals(account.getPassword())) {
            return new AccountResponse(
                    account.getAccountId(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getPublicKeyActive(),
                    account.getRole()
            );
        }
        return null;
    }

    public AccountResponse getAccountById(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            return new AccountResponse(
                    account.getAccountId(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getPublicKeyActive(),
                    account.getRole()
            );
        }
        return null;
    }

    public boolean updateProfile(long accountId, UpdateProfileRequest updateProfileRequest) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setUsername(updateProfileRequest.username().trim());
            account.setEmail(updateProfileRequest.email().trim());
            return accountDao.update(account);
        }
        return false;
    }

    public boolean changePassword(long accountId, ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountById(accountId);
        if (account != null && SignatureUtil.hash(changePasswordRequest.oldPassword()).equals(account.getPassword())) {
            account.setPassword(SignatureUtil.hash(changePasswordRequest.newPassword()));
            return accountDao.update(account);
        }
        return false;
    }

    public boolean updatePassword(long accountId, String newPassword) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setPassword(SignatureUtil.hash(newPassword));
            return accountDao.update(account);
        }
        return false;
    }

    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email.trim());
    }

    public boolean updatePublicKey(long accountId, UpdatePublicKeyRequest updatePublicKeyRequest) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setPublicKeyActive(updatePublicKeyRequest.publicKey().trim());
            return accountDao.update(account);
        }
        return false;
    }

    public boolean revokePublicKey(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setPublicKeyActive(null);
            return accountDao.update(account);
        }
        return false;
    }

    public String getPublicKeyActive(long accountId) {
        return accountDao.getPublicKeyActive(accountId);
    }

    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}