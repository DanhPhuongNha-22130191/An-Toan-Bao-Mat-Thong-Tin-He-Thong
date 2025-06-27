package com.atbm.controllers.auth;

import com.atbm.models.entity.Account;
import com.atbm.services.AccountService;
import com.atbm.utils.EmailUtils;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import com.atbm.services.RecaptchaService;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller xử lý chức năng quên mật khẩu.
 */
@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private AccountService accountService;
    private EmailUtils emailUtil;

    /**
     * Phương thức khởi tạo các service khi servlet được tạo.
     */
    @Override
    public void init() throws ServletException {
        try {
            accountService = CDI.current().select(AccountService.class).get();
            emailUtil = new EmailUtils();
        } catch (Exception e) {
            LogUtils.debug(ForgotPasswordController.class, "Khởi tạo ForgotPasswordController thất bại: " + e.getMessage());
            throw new ServletException("Khởi tạo ForgotPasswordController thất bại", e);
        }
    }

    /**
     * Xử lý hiển thị modal quên mật khẩu khi người dùng gửi yêu cầu GET.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    /**
     * Xử lý yêu cầu quên mật khẩu khi người dùng gửi form POST.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin reCAPTCHA từ form và xác minh
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse);
        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        // Lấy email người dùng nhập
        String email = req.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập email.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        try {
            // Tìm tài khoản theo email
            Account account = accountService.getAccountByEmail(email.trim());
            if (account != null) {
                // Tạo mật khẩu mới ngẫu nhiên và cập nhật
                String newPassword = accountService.generateRandomPassword();
                boolean updated = accountService.updatePassword(account.getAccountId(), newPassword);
                if (updated) {
                    // Gửi email chứa mật khẩu mới
                    String emailContent = "Chào bạn,\n\n" +
                            "Mật khẩu mới của bạn là: " + newPassword + "\n" +
                            "Vui lòng sử dụng mật khẩu này để đăng nhập và đổi mật khẩu sớm nhất có thể.\n\n" +
                            "Trân trọng,\nWatchShop";
                    boolean emailSent = emailUtil.sendEmail(email, "Khôi phục mật khẩu", emailContent);
                    if (emailSent) {
                        HttpUtils.setAttribute(req, "message", "Mật khẩu mới đã được gửi đến email của bạn.");
                    } else {
                        HttpUtils.setAttribute(req, "error", "Gửi email thất bại. Vui lòng thử lại sau.");
                        HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
                    }
                } else {
                    HttpUtils.setAttribute(req, "error", "Không thể cập nhật mật khẩu. Vui lòng thử lại.");
                    HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
                }
            } else {
                HttpUtils.setAttribute(req, "error", "Email không tồn tại trong hệ thống.");
                HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            }
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        } catch (Exception e) {
            LogUtils.debug(ForgotPasswordController.class, "Lỗi khi xử lý yêu cầu khôi phục mật khẩu: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi gửi yêu cầu khôi phục: " + e.getMessage());
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        }
    }
}