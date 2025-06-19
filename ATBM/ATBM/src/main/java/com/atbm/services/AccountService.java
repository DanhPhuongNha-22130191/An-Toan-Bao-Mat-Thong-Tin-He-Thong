package com.atbm.services;

import com.atbm.dao.account.AccountDao;
import com.atbm.dao.account.impl.AccountDaoImpl;
import com.atbm.models.entity.Account;
import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.RegisterRequest;
import com.atbm.models.wrapper.request.LoginRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.utils.EmailUtils;
import com.atbm.utils.SignatureUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpl();
    }

    public AccountResponse getUserInfo(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        return new AccountResponse(
                account.getAccountId(),
                account.getUsername(),
                account.getEmail(),
                account.getPublicKeyActive(),
                account.getRole()
        );
    }

    public void register(RegisterRequest registerRequest) throws NoSuchAlgorithmException {
        String username = registerRequest.username().trim();
        String email = registerRequest.email().trim();
        String password = registerRequest.password().trim();
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin đăng ký.");
        }
        if (accountDao.existsByUsername(username)) {
            throw new RuntimeException("Tên người dùng đã tồn tại.");
        }
        if (accountDao.getAccountByEmail(email) != null) {
            throw new RuntimeException("Email đã được sử dụng.");
        }
        String hashedPassword = SignatureUtils.hash(password);
        Account account = new Account(username, hashedPassword, email);
        try {
            accountDao.insert(account);
        } catch (RuntimeException e) {
            throw new RuntimeException("Đăng ký thất bại: " + e.getMessage());
        }
    }

    public AccountResponse login(LoginRequest loginRequest) throws NoSuchAlgorithmException {
        String username = loginRequest.username().trim();
        String password = loginRequest.password().trim();
        if (username.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
        }
        Account account = accountDao.getAccountByUsername(username);
        if (account == null) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        if (!SignatureUtils.hash(password).equals(account.getPassword())) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu.");
        }
        return new AccountResponse(
                account.getAccountId(),
                account.getUsername(),
                account.getEmail(),
                account.getPublicKeyActive(),
                account.getRole()
        );
    }

    public void updateProfile(long accountId, UpdateProfileRequest updateProfileRequest) {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        String newUsername = updateProfileRequest.username().trim();
        String newEmail = updateProfileRequest.email().trim();
        if (newUsername.isEmpty() || newEmail.isEmpty()) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin.");
        }
        if (accountDao.existsByUsername(newUsername) && !newUsername.equals(account.getUsername())) {
            throw new RuntimeException("Tên người dùng đã tồn tại.");
        }
        if (accountDao.getAccountByEmail(newEmail) != null && !newEmail.equals(account.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng.");
        }
        // Kiểm tra nếu không có thay đổi
        if (newUsername.equals(account.getUsername()) && newEmail.equals(account.getEmail())) {
            throw new RuntimeException("Không có thay đổi nào để cập nhật.");
        }
        account.setUsername(newUsername);
        account.setEmail(newEmail);
        try {
            accountDao.update(account);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cập nhật hồ sơ thất bại: " + e.getMessage());
        }
    }

    public void changePassword(long accountId, ChangePasswordRequest request) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        String oldPassword = request.oldPassword().trim();
        String newPassword = request.newPassword().trim();
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            throw new RuntimeException("Vui lòng nhập đầy đủ mật khẩu.");
        }
        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("Mật khẩu mới không được giống mật khẩu cũ.");
        }
        if (!SignatureUtils.hash(oldPassword).equals(account.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng.");
        }
        account.setPassword(SignatureUtils.hash(newPassword));
        try {
            accountDao.update(account);
        } catch (RuntimeException e) {
            throw new RuntimeException("Thay đổi mật khẩu thất bại: " + e.getMessage());
        }
    }

    public void updatePassword(long accountId, String newPassword) throws NoSuchAlgorithmException {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        if (newPassword.trim().isEmpty()) {
            throw new RuntimeException("Mật khẩu mới không được để trống.");
        }
        account.setPassword(SignatureUtils.hash(newPassword));
        try {
            accountDao.update(account);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cập nhật mật khẩu thất bại: " + e.getMessage());
        }
    }

    public Account getAccountByEmail(String email) {
        try {
            Account account = accountDao.getAccountByEmail(email.trim());
            if (account == null) {
                throw new RuntimeException("Email không tồn tại trong hệ thống.");
            }
            if (account.isDelete()) {
                throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
            }
            return account;
        } catch (RuntimeException e) {
            throw new RuntimeException("Lỗi khi lấy tài khoản theo email: " + e.getMessage());
        }
    }

    public void resetPassword(String email) throws NoSuchAlgorithmException {
        Account account = getAccountByEmail(email);
        String newPassword = generateRandomPassword();
        updatePassword(account.getAccountId(), newPassword);
        String emailContent = "Chào bạn,\n\n" +
                "Mật khẩu mới của bạn là: " + newPassword + "\n" +
                "Vui lòng sử dụng mật khẩu này để đăng nhập và đổi mật khẩu sớm nhất có thể.\n\n" +
                "Trân trọng,\nWatchShop";
        if (!EmailUtils.sendEmail(email, "Khôi phục mật khẩu", emailContent)) {
            throw new RuntimeException("Gửi email thất bại. Vui lòng thử lại sau.");
        }
    }

    public void updatePublicKey(long accountId, UpdatePublicKeyRequest updatePublicKeyRequest) {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        String publicKey = updatePublicKeyRequest.publicKey().trim();
        if (publicKey.isEmpty()) {
            throw new RuntimeException("Vui lòng nhập hoặc tải lên khóa công khai.");
        }
        account.setPublicKeyActive(publicKey);
        try {
            accountDao.update(account);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cập nhật khóa công khai thất bại: " + e.getMessage());
        }
    }

    public void revokePublicKey(long accountId) {
        Account account = accountDao.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Tài khoản không tồn tại.");
        }
        if (account.isDelete()) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa.");
        }
        account.setPublicKeyActive(null);
        try {
            accountDao.update(account);
        } catch (RuntimeException e) {
            throw new RuntimeException("Hủy khóa công khai thất bại: " + e.getMessage());
        }
    }

    public String getPublicKeyActive(long accountId) {
        try {
            String publicKey = accountDao.getPublicKeyActive(accountId);
            if (publicKey == null) {
                throw new RuntimeException("Không tìm thấy khóa công khai.");
            }
            return publicKey;
        } catch (RuntimeException e) {
            throw new RuntimeException("Lỗi khi lấy khóa công khai: " + e.getMessage());
        }
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