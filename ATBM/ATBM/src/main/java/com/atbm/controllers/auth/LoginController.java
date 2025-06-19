package com.atbm.controllers.auth;

import com.atbm.models.wrapper.request.LoginRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import com.atbm.services.Recaptcha;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        if (!Recaptcha.verify(recaptchaResponse)) {
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
            HttpSession session = req.getSession();
            session.setAttribute("accountId", account.accountId());
//            resp.sendRedirect(req.getContextPath() + "/product/???");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        } catch (NoSuchAlgorithmException e) {
            LogUtils.debug(LoginController.class, "Lỗi hệ thống: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi hệ thống khi đăng nhập.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
        }
    }
}