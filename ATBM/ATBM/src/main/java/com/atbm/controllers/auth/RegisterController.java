package com.atbm.controllers.auth;


import com.atbm.config.BaseController;
import com.atbm.mapper.FormMapper;
import com.atbm.models.wrapper.request.RegisterRequest;
import com.atbm.services.AccountService;
import com.atbm.services.RecaptchaService;
import com.atbm.utils.ConfigUtils;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends BaseController {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = CDI.current().select(AccountService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse, ConfigUtils.get("recaptcha.secret"));
        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        RegisterRequest registerRequest = FormMapper.bind(req.getParameterMap(), RegisterRequest.class);
        String confirmPassword = req.getParameter("confirmPassword");

        if (registerRequest.getUsername() == null || registerRequest.getEmail() == null ||
                registerRequest.getPassword() == null || confirmPassword == null ||
                registerRequest.getEmail().trim().isEmpty() || registerRequest.getUsername().trim().isEmpty() ||
                registerRequest.getPassword().trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin đăng ký.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        if (!registerRequest.getPassword().equals(confirmPassword)) {
            HttpUtils.setAttribute(req, "error", "Mật khẩu xác nhận không khớp.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        if (!registerRequest.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập email hợp lệ.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        try {
            boolean registered = accountService.register(registerRequest);
            if (registered) {
                HttpUtils.sendRedirect(req, resp, "/login?success=registered");
            } else {
                HttpUtils.setAttribute(req, "error", "Đăng ký thất bại. Tên người dùng hoặc email đã tồn tại.");
                HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            }
        } catch (Exception e) {
            LogUtils.debug(RegisterController.class, "Lỗi khi đăng ký: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi đăng ký: " + e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
        }
    }
}