package com.atbm.test;
import com.atbm.models.Account;
import com.atbm.dao.AccountDao;
import java.util.List;

public class AccountDaoTest {

    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao();

        // Test thêm tài khoản mới
        System.out.println("Kiểm tra thêm tài khoản...");
        Account newAccount = new Account(0, "testuser", "testpassword", "test@example.com");
        boolean insertResult = accountDao.insert(newAccount);
        System.out.println("Kết quả thêm: " + (insertResult ? "Thành công" : "Thất bại"));

        // Test lấy tài khoản theo username
        System.out.println("\nKiểm tra lấy tài khoản theo username...");
        Account account = accountDao.getByUsername("testuser");
        if (account != null) {
            System.out.println("Tài khoản tìm thấy: " + account.getUsername() + ", " + account.getEmail());
        } else {
            System.out.println("Không tìm thấy tài khoản");
        }

        // Test lấy tất cả tài khoản
        System.out.println("\nKiểm tra lấy tất cả tài khoản...");
        List<Account> accounts = accountDao.getAll();
        if (accounts.isEmpty()) {
            System.out.println("Không có tài khoản nào trong danh sách");
        } else {
            for (Account acc : accounts) {
                System.out.println("Tài khoản: " + acc.getUsername() + ", " + acc.getEmail());
            }
        }

        // Test kiểm tra username tồn tại
        System.out.println("\nKiểm tra username tồn tại...");
        boolean exists = accountDao.existsByUsername("testuser");
        System.out.println("Username 'testuser' tồn tại: " + exists);

        // Test kiểm tra username không tồn tại
        boolean nonExists = accountDao.existsByUsername("nonexistinguser");
        System.out.println("Username 'nonexistinguser' tồn tại: " + nonExists);
    }
}
