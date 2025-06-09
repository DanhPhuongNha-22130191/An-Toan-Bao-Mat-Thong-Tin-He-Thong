package com.atbm.controllers;

import com.atbm.models.Account;
import com.atbm.services.AccountService;
import com.atbm.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/admin/users")
public class AdminUserController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AdminUserController.class.getName());
    private AccountService accountService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        try {
            accountService = new AccountService();
            orderService = new OrderService();
            LOGGER.info("AdminUserController khởi tạo thành công");
        } catch (Exception e) {
            LOGGER.severe("Lỗi khởi tạo AdminUserController: " + e.getMessage());
            throw new ServletException("Không thể khởi tạo AdminUserController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                long userId = Long.parseLong(req.getParameter("userId"));
                if (orderService.hasOrders(userId)) {
                    req.setAttribute("error", "Không thể xóa người dùng đã có đơn hàng.");
                } else {
                    boolean success = accountService.delete(userId);
                    if (success) {
                        req.setAttribute("message", "Xóa người dùng thành công!");
                    } else {
                        req.setAttribute("error", "Không thể xóa người dùng.");
                    }
                }
            }

            List<Account> users = accountService.getAll();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/views/userAdmin.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            LOGGER.severe("Lỗi định dạng số: " + e.getMessage());
            req.setAttribute("error", "ID người dùng không hợp lệ.");
            List<Account> users = accountService.getAll();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/views/userAdmin.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
            req.setAttribute("error", "Không thể tải danh sách người dùng.");
            req.getRequestDispatcher("/views/userAdmin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                String email = req.getParameter("email");

                if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                    req.setAttribute("error", "Tên đăng nhập và mật khẩu không được để trống.");
                } else {
                    boolean success = accountService.register(username.trim(), password.trim(), email != null ? email.trim() : null);
                    if (success) {
                        req.setAttribute("message", "Thêm người dùng thành công!");
                    } else {
                        req.setAttribute("error", "Không thể thêm người dùng. Tên đăng nhập có thể đã tồn tại.");
                    }
                }
            } else if ("update".equals(action)) {
                long userId = Long.parseLong(req.getParameter("userId"));
                String username = req.getParameter("username");
                String email = req.getParameter("email");

                Account account = accountService.getById(userId);
                if (account != null) {
                    account.setUsername(username != null ? username.trim() : account.getUsername());
                    account.setEmail(email != null ? email.trim() : account.getEmail());
                    boolean success = accountService.update(account);
                    if (success) {
                        req.setAttribute("message", "Cập nhật người dùng thành công!");
                    } else {
                        req.setAttribute("error", "Không thể cập nhật người dùng.");
                    }
                } else {
                    req.setAttribute("error", "Không tìm thấy người dùng.");
                }
            }

            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;

        } catch (NumberFormatException e) {
            LOGGER.severe("Lỗi định dạng số khi xử lý POST action " + action + ": " + e.getMessage());
            req.setAttribute("error", "ID người dùng không hợp lệ.");
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi xử lý POST action " + action + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi xử lý yêu cầu.");
        }

        doGet(req, resp);
    }
}