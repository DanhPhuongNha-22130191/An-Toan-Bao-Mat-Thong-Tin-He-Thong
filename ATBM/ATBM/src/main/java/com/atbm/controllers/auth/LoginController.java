package com.atbm.controllers.auth;

import com.atbm.models.wrapper.request.LoginRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import com.atbm.services.RecaptchaService;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        try {
            accountService = CDI.current().select(AccountService.class).get();
        } catch (Exception e) {
            LogUtils.debug(LoginController.class, "Khởi tạo LoginController thất bại: " + e.getMessage());
            throw new ServletException("Khởi tạo LoginController thất bại", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse);

        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        try {
            LoginRequest loginRequest = new LoginRequest(username.trim(), password.trim());
            AccountResponse account = accountService.login(loginRequest);

            if (account != null) {
                HttpSession session = req.getSession();
                session.setAttribute("accountId", account.accountId());
                session.setAttribute("user", account); // Lưu user vào session
                resp.sendRedirect(req.getContextPath() + "/product/category");
            } else {
                HttpUtils.setAttribute(req, "error", "Sai tài khoản hoặc mật khẩu.");
                HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            }
        } catch (Exception e) {
            LogUtils.debug(LoginController.class, "Lỗi khi đăng nhập: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi đăng nhập: " + e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        }
    }
}