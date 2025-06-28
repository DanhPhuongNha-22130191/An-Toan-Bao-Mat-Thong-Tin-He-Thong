package com.atbm.controllers.auth;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.request.LoginRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.ConfigUtils;
import com.atbm.mapper.FormMapper;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import com.atbm.services.RecaptchaService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends BaseController {
    @Inject
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse, ConfigUtils.get("recaptcha.secret"));
        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        LoginRequest loginRequest = FormMapper.bind(req.getParameterMap(), LoginRequest.class);
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null ||
                loginRequest.getUsername().trim().isEmpty() || loginRequest.getPassword().trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        try {
            AccountResponse account = accountService.login(loginRequest);
            if (account != null) {
                HttpSession session = req.getSession();
                session.setAttribute("accountId", account.getAccountId());
                session.setAttribute("user", account);
                HttpUtils.sendRedirect(req, resp, "/home");
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