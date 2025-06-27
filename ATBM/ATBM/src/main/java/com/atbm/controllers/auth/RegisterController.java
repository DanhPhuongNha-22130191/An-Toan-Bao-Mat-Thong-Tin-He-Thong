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

/**
 * Controller xử lý chức năng đăng ký tài khoản người dùng.
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private AccountService accountService;

    /**
     * Phương thức khởi tạo servlet. Được gọi khi servlet được khởi chạy lần đầu.
     */
    @Override
    public void init() throws ServletException {
        try {
            accountService = CDI.current().select(AccountService.class).get();
        } catch (Exception e) {
            LogUtils.debug(RegisterController.class, "Khởi tạo RegisterController thất bại: " + e.getMessage());
            throw new ServletException("Khởi tạo RegisterController thất bại", e);
        }
    }

    /**
     * Hiển thị trang đăng ký khi người dùng truy cập đường dẫn /register (GET).
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/register.jsp");
    }

    /**
     * Xử lý dữ liệu khi người dùng gửi form đăng ký (POST).
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy mã phản hồi từ Google reCAPTCHA
        String recaptchaResponse = req.getParameter("g-recaptcha-response");

        // Kiểm tra người dùng có vượt qua reCAPTCHA hay không
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse);
        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        // Lấy thông tin người dùng từ form
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Kiểm tra đầu vào hợp lệ
        if (username == null || email == null || password == null ||
                username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin đăng ký.");
            HttpUtils.dispatcher(req, resp, "/views/register.jsp");
            return;
        }

        try {
            // Tạo đối tượng request cho việc đăng ký
            RegisterRequest registerRequest = new RegisterRequest(username.trim(), password.trim(), email.trim());

            // Thực hiện đăng ký tài khoản
            boolean registered = accountService.register(registerRequest);

            if (registered) {
                // Nếu thành công, chuyển hướng người dùng về trang đăng nhập
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                // Nếu thất bại (ví dụ tài khoản hoặc email đã tồn tại), hiển thị thông báo lỗi
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