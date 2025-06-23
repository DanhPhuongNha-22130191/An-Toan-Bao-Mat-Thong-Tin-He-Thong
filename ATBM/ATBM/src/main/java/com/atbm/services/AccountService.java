package com.atbm.services;

import com.atbm.dao.account.AccountDao;
import com.atbm.dao.account.impl.AccountDaoImpl;
import com.atbm.models.entity.Account;
import com.atbm.models.wrapper.request.AddAccountRequest;
import com.atbm.models.wrapper.request.EditAccountRequest;
import com.atbm.models.wrapper.request.RegisterRequest;
import com.atbm.models.wrapper.request.LoginRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.utils.SignatureUtil;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpl();
    }

    // Đăng ký tài khoản từ RegisterRequest
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

    // Đăng ký tài khoản từ AddAccountRequest
    public boolean register(AddAccountRequest request) throws NoSuchAlgorithmException {
        if (accountDao.existsByUsername(request.username().trim())) {
            return false;
        }
        String hashedPassword = SignatureUtil.hash(request.password());
        Account account = new Account();
        account.setUsername(request.username().trim());
        account.setPassword(hashedPassword);
        account.setEmail(request.email() != null ? request.email().trim() : null);
        if (request.role() != null) {
            account.setRole(request.role());
        }
        // Mặc định tài khoản mới không bị xóa
        account.setDeleted(request.isDeleted() != null ? request.isDeleted() : false);
        return accountDao.insert(account);
    }

    // Đăng nhập
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

    // Lấy thông tin tài khoản theo ID
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

    // Cập nhật thông tin profile
    public boolean updateProfile(long accountId, UpdateProfileRequest updateProfileRequest) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setUsername(updateProfileRequest.username().trim());
            account.setEmail(updateProfileRequest.email().trim());
            return accountDao.update(account);
        }
        return false;
    }

    // Đổi mật khẩu có kiểm tra mật khẩu cũ
    public boolean changePassword(long accountId, ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountById(accountId);
        if (account != null && SignatureUtil.hash(changePasswordRequest.oldPassword()).equals(account.getPassword())) {
            account.setPassword(SignatureUtil.hash(changePasswordRequest.newPassword()));
            return accountDao.update(account);
        }
        return false;
    }

    // Cập nhật mật khẩu trực tiếp
    public boolean updatePassword(long accountId, String newPassword) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setPassword(SignatureUtil.hash(newPassword));
            return accountDao.update(account);
        }
        return false;
    }

    // Lấy tài khoản theo email
    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email.trim());
    }

    // Cập nhật khóa công khai
    public boolean updatePublicKey(long accountId, UpdatePublicKeyRequest updatePublicKeyRequest) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setPublicKeyActive(updatePublicKeyRequest.publicKey().trim());
            return accountDao.update(account);
        }
        return false;
    }

    // Thu hồi khóa công khai
    public boolean revokePublicKey(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null) {
            account.setPublicKeyActive(null);
            return accountDao.update(account);
        }
        return false;
    }

    // Lấy khóa công khai đang hoạt động
    public String getPublicKeyActive(long accountId) {
        return accountDao.getPublicKeyActive(accountId);
    }

    // Tạo mật khẩu ngẫu nhiên
    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    // Lấy danh sách tài khoản (bao gồm cả tài khoản bị xóa)
    public List<AccountResponse> getAccounts() {
        List<AccountResponse> accountResponses = new ArrayList<>();
        List<Account> accounts = accountDao.getAccounts();
        for (Account account : accounts) {
            accountResponses.add(new AccountResponse(
                    account.getAccountId(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getPublicKeyActive(),
                    account.getRole()
            ));
        }
        return accountResponses;
    }

    // Cập nhật tài khoản từ EditAccountRequest
    public boolean update(EditAccountRequest request) {
        Account account = accountDao.getAccountById(request.accountId());
        if (account == null) return false;

        if (request.username() != null && !request.username().isBlank()) {
            account.setUsername(request.username().trim());
        }
        if (request.email() != null) {
            account.setEmail(request.email().trim());
        }
        if (request.role() != null) {
            account.setRole(request.role());
        }
        if (request.isDeleted() != null) {
            account.setDeleted(request.isDeleted());
        }
        return accountDao.update(account);
    }

    // Xóa tài khoản (đánh dấu isDeleted = true)
    public boolean delete(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account != null && !account.isDeleted()) {
            account.setDeleted(true);
            return accountDao.update(account);
        }
        return false;
    }
}
