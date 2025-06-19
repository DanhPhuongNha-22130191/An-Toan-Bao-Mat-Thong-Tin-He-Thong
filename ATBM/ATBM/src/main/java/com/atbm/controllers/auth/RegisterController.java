package com.atbm.controllers.auth;

import com.atbm.models.wrapper.request.RegisterRequest;
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

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        if (!Recaptcha.verify(recaptchaResponse)) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (username == null || email == null || password == null ||
                username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin đăng ký.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        try {
            RegisterRequest registerRequest = new RegisterRequest(username.trim(), password.trim(), email.trim());
            accountService.register(registerRequest);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
        } catch (NoSuchAlgorithmException e) {
            LogUtils.debug(RegisterController.class, "Lỗi hệ thống: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi hệ thống khi đăng ký.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
        }
    }
}