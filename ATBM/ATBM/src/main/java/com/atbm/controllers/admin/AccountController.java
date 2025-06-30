package com.atbm.controllers.admin;

import com.atbm.config.BaseController;
import com.atbm.models.entity.Account;
import com.atbm.models.enums.Role;
import com.atbm.models.wrapper.request.AddAccountRequest;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AccountController extends BaseController {
    private AccountService accountService;

    @Override
    public void init() {
        accountService = CDI.current().select(AccountService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accounts = accountService.getAccounts();
        HttpUtils.setAttribute(req, "accounts", accounts);

        String message = (String) req.getSession().getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            req.getSession().removeAttribute("message");
        }
        String error = (String) req.getSession().getAttribute("error");
        if (error != null) {
            req.setAttribute("error", error);
            req.getSession().removeAttribute("error");
        }

        HttpUtils.dispatcher(req, resp, "/views/userAdmin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("suspend".equals(action)) {
            String userIdStr = req.getParameter("userId");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                req.getSession().setAttribute("error", "ID người dùng không hợp lệ");
            } else {
                try {
                    long userId = Long.parseLong(userIdStr);
                    boolean success = accountService.suspend(userId);
                    if (success) {
                        req.getSession().setAttribute("message", "Thay đổi trạng thái người dùng thành công");
                    } else {
                        req.getSession().setAttribute("error", "Tài khoản không tồn tại hoặc không thể thay đổi trạng thái.");
                    }
                } catch (NumberFormatException e) {
                    req.getSession().setAttribute("error", "ID người dùng không hợp lệ");
                }
            }
            HttpUtils.sendRedirect(req, resp, "/admin/users");
        } else {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String roleStr = req.getParameter("role");
            Role role = (roleStr != null && !roleStr.isBlank()) ? Role.valueOf(roleStr) : null;

            AddAccountRequest addRequest = new AddAccountRequest(username, password, email, role, false);
            try {
                accountService.register(addRequest);
                req.getSession().setAttribute("message", "Thêm người dùng thành công");
            } catch (RuntimeException e) {
                req.getSession().setAttribute("error", e.getMessage());
            }
            HttpUtils.sendRedirect(req, resp, "/admin/users");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userIdStr = req.getParameter("userId");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                req.setAttribute("error", "ID người dùng không hợp lệ");
                HttpUtils.dispatcher(req, resp, "/views/userAdmin.jsp");
                return;
            }

            long userId = Long.parseLong(userIdStr);
            boolean success = accountService.suspend(userId);
            if (success) {
                req.getSession().setAttribute("message", "Thay đổi trạng thái người dùng thành công");
                HttpUtils.sendRedirect(req, resp, "/admin/users");
            } else {
                req.setAttribute("error", "Tài khoản không tồn tại hoặc không thể thay đổi trạng thái.");
                HttpUtils.dispatcher(req, resp, "/views/userAdmin.jsp");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID người dùng không hợp lệ");
            HttpUtils.dispatcher(req, resp, "/views/userAdmin.jsp");
        }
    }
}