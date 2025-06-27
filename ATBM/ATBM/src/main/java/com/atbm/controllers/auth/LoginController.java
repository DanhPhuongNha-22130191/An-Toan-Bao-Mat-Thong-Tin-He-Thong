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

/**
 * Controller xử lý chức năng đăng nhập cho người dùng.
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
    private AccountService accountService;

    /**
     * Hàm khởi tạo servlet, đồng thời khởi tạo các service cần thiết.
     */
    @Override
    public void init() throws ServletException {
        try {
            accountService = CDI.current().select(AccountService.class).get();
        } catch (Exception e) {
            LogUtils.debug(LoginController.class, "Khởi tạo LoginController thất bại: " + e.getMessage());
            throw new ServletException("Khởi tạo LoginController thất bại", e);
        }
    }

    /**
     * Xử lý yêu cầu GET – điều hướng người dùng tới trang đăng nhập.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, "/views/login.jsp");
    }

    /**
     * Xử lý yêu cầu POST – thực hiện kiểm tra reCAPTCHA, xác thực người dùng và tạo phiên làm việc.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy mã phản hồi reCAPTCHA từ client và xác minh
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = RecaptchaService.verify(recaptchaResponse);

        if (!isRecaptchaValid) {
            HttpUtils.setAttribute(req, "error", "Vui lòng xác nhận bạn không phải là robot.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        // Lấy thông tin tên đăng nhập và mật khẩu từ form
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Kiểm tra xem người dùng đã nhập đầy đủ chưa
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            HttpUtils.dispatcher(req, resp, "/views/login.jsp");
            return;
        }

        try {
            // Tạo đối tượng LoginRequest và thực hiện đăng nhập qua service
            LoginRequest loginRequest = new LoginRequest(username.trim(), password.trim());
            AccountResponse account = accountService.login(loginRequest);

            if (account != null) {
                // Nếu đăng nhập thành công, tạo session và chuyển hướng người dùng
                HttpSession session = req.getSession();
                session.setAttribute("accountId", account.accountId());
                resp.sendRedirect(req.getContextPath() + "/product/category");
            } else {
                // Nếu thông tin đăng nhập không đúng
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