package com.atbm.services;

import com.atbm.dao.AccountDao;
import com.atbm.dto.AccountDTO;
import com.atbm.models.Account;
import com.atbm.utils.SignatureUtil;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.logging.Logger;

public class AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());
    private final AccountDao accountDao;

    public AccountService() {
        try {
            accountDao = new AccountDao();
            LOGGER.info("AccountService khởi tạo thành công");
        } catch (Exception e) {
            LOGGER.severe("Không thể khởi tạo AccountDao: " + e.getMessage());
            throw new RuntimeException("Không thể khởi tạo AccountService", e);
        }
    }

    public boolean register(String username, String password, String email) {
        try {
            if (accountDao.existsByUsername(username.trim())) {
                LOGGER.warning("Tên người dùng đã tồn tại: " + username);
                return false;
            }
            String hashedPassword = SignatureUtil.hash(password);
            LOGGER.info("Đăng ký: Băm mật khẩu cho người dùng " + username);
            Account account = new Account(username.trim(), hashedPassword, email.trim());
            return accountDao.insert(account);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi đăng ký người dùng " + username + ": " + e.getMessage());
            return false;
        }
    }

    public Account login(String username, String password) {
        try {
            Account account = accountDao.getByUsername(username.trim());
            if (account != null) {
                String hashedInputPassword = SignatureUtil.hash(password);
                LOGGER.info("Đăng nhập: So sánh mật khẩu băm cho người dùng " + username + ", Mật khẩu băm: " + hashedInputPassword);
                if (hashedInputPassword.equals(account.getPassword())) {
                    LOGGER.info("Mật khẩu khớp cho người dùng: " + username);
                    return account;
                } else {
                    LOGGER.info("Mật khẩu không khớp cho người dùng: " + username + ", Mật khẩu trong DB: " + account.getPassword());
                }
            } else {
                LOGGER.info("Không tìm thấy người dùng: " + username);
            }
            return null;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi đăng nhập cho người dùng " + username + ": " + e.getMessage());
            return null;
        }
    }

    public boolean updateProfile(AccountDTO user) {
        try {
            Account account = accountDao.getById(user.getAccountId());
            if (account != null) {
                account.setUsername(user.getUsername());
                account.setEmail(user.getEmail());
                account.setPublicKeyActive(user.getPublicKeyActive());
                return accountDao.update(account);
            }
            return false;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi cập nhật hồ sơ cho account ID " + user.getAccountId() + ": " + e.getMessage());
            return false;
        }
    }

    public boolean checkPassword(long accountId, String password) {
        try {
            Account account = accountDao.getById(accountId);
            if (account != null) {
                String hashedPassword = SignatureUtil.hash(password);
                return hashedPassword.equals(account.getPassword());
            }
            return false;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi kiểm tra mật khẩu cho account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    public boolean updatePassword(long accountId, String newPassword) {
        try {
            Account account = accountDao.getById(accountId);
            if (account != null) {
                String hashedPassword = SignatureUtil.hash(newPassword);
                LOGGER.info("Cập nhật mật khẩu: Băm mật khẩu mới cho account ID " + accountId + ", Mật khẩu băm: " + hashedPassword);
                account.setPassword(hashedPassword);
                boolean updated = accountDao.update(account);
                if (updated) {
                    LOGGER.info("Cập nhật mật khẩu thành công cho account ID " + accountId);
                } else {
                    LOGGER.warning("Cập nhật mật khẩu thất bại cho account ID " + accountId);
                }
                return updated;
            }
            LOGGER.warning("Không tìm thấy tài khoản với account ID: " + accountId);
            return false;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi cập nhật mật khẩu cho account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    public Account getAccountByEmail(String email) {
        try {
            Account account = accountDao.getAccountByEmail(email);
            if (account != null) {
                LOGGER.info("Tìm thấy tài khoản với email: " + email);
            } else {
                LOGGER.info("Không tìm thấy tài khoản với email: " + email);
            }
            return account;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy tài khoản bằng email " + email + ": " + e.getMessage());
            return null;
        }
    }

    public String generatePublicKey(long accountId) {
        try {
            Account account = accountDao.getById(accountId);
            if (account != null) {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048);
                KeyPair pair = keyGen.generateKeyPair();
                String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
                account.setPublicKeyActive(publicKey);
                if (accountDao.update(account)) {
                    LOGGER.info("Tạo public key thành công cho account ID: " + accountId);
                    return publicKey;
                }
            }
            LOGGER.warning("Tạo public key thất bại cho account ID: " + accountId);
            return null;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi tạo public key cho account ID " + accountId + ": " + e.getMessage());
            return null;
        }
    }

    public void revokePublicKey(long accountId) {
        try {
            Account account = accountDao.getById(accountId);
            if (account != null) {
                account.setPublicKeyActive(null);
                accountDao.update(account);
                LOGGER.info("Thu hồi public key thành công cho account ID: " + accountId);
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi thu hồi public key cho account ID " + accountId + ": " + e.getMessage());
        }
    }

    public String getPublicKeyIsActive(long accountId) {
        try {
            Account account = accountDao.getById(accountId);
            if (account != null) {
                String publicKey = account.getPublicKeyActive();
                LOGGER.info("Lấy publicKeyActive cho account ID " + accountId + ": " + (publicKey != null ? publicKey : "null"));
                return publicKey;
            }
            LOGGER.warning("Không tìm thấy tài khoản với account ID: " + accountId);
            return null;
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy publicKeyActive cho account ID " + accountId + ": " + e.getMessage());
            return null;
        }
    }
}