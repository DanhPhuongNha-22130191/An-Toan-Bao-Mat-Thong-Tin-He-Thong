package com.atbm.controllers.auth;

import com.atbm.models.entity.Account;
import com.atbm.services.AccountService;
import com.atbm.utils.EmailUtils;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import com.atbm.services.RecaptchaService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    @Inject
    private AccountService accountService;
    @Inject
    private EmailUtils emailUtil;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse);
        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        String email = req.getParameter("email");
        if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập email hợp lệ.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        try {
            Account account = accountService.getAccountByEmail(email.trim());
            if (account != null) {
                String newPassword = accountService.generateRandomPassword();
                boolean updated = accountService.updatePassword(account.getAccountId(), newPassword);
                if (updated) {
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