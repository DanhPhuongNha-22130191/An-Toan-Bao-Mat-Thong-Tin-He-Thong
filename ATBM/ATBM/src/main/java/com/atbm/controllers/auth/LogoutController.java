package com.atbm.controllers.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller xử lý chức năng đăng xuất người dùng.
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    /**
     * Xử lý yêu cầu GET để thực hiện đăng xuất người dùng.
     * Khi người dùng truy cập đường dẫn /logout, phiên làm việc sẽ bị hủy và chuyển hướng về trang đăng nhập.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy phiên làm việc hiện tại (nếu tồn tại)
        HttpSession session = req.getSession(false);

        // Nếu phiên làm việc tồn tại, tiến hành hủy (đăng xuất)
        if (session != null) {
            session.invalidate();
        }

        // Chuyển hướng người dùng về trang đăng nhập
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}