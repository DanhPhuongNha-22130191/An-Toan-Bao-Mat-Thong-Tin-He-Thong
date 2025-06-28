package com.atbm.controllers.auth;

import com.atbm.models.wrapper.request.RegisterRequest;
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
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        try {
            accountService = CDI.current().select(AccountService.class).get();
        } catch (Exception e) {
            LogUtils.debug(RegisterController.class, "Khởi tạo RegisterController thất bại: " + e.getMessage());
            throw new ServletException("Khởi tạo RegisterController thất bại", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse);
        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        if (username == null || email == null || password == null || confirmPassword == null ||
                username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin đăng ký.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        if (!password.equals(confirmPassword)) {
            HttpUtils.setAttribute(req, "error", "Mật khẩu xác nhận không khớp.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập email hợp lệ.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        try {
            RegisterRequest registerRequest = new RegisterRequest(username.trim(), password.trim(), email.trim());
            boolean registered = accountService.register(registerRequest);

            if (registered) {
                resp.sendRedirect(req.getContextPath() + "/login?success=registered");
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