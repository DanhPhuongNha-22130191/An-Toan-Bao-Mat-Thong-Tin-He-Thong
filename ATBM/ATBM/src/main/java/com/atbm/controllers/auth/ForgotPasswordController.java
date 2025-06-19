package com.atbm.controllers.auth;

import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import com.atbm.services.Recaptcha;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        if (!Recaptcha.verify(recaptchaResponse)) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        String email = req.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập email.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        try {
            accountService.resetPassword(email.trim());
            HttpUtils.setAttribute(req, "message", "Mật khẩu mới đã được gửi đến email của bạn.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        } catch (NoSuchAlgorithmException e) {
            LogUtils.debug(ForgotPasswordController.class, "Lỗi hệ thống: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi hệ thống khi khôi phục mật khẩu.");
            HttpUtils.setAttribute(req, "showForgotPasswordModal", true);
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        }
    }
}