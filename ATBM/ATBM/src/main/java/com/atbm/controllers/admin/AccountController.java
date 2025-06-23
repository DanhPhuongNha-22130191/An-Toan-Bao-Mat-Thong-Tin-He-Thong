package com.atbm.controllers.admin;

import com.atbm.models.enums.Role;
import com.atbm.models.wrapper.request.AddAccountRequest;
import com.atbm.models.wrapper.request.EditAccountRequest;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/admin/users")
public class AccountController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var accounts = accountService.getAccounts();
        HttpUtils.setAttribute(req, "accounts", accounts);
        HttpUtils.dispatcher(req, resp, "/WEB-INF/views/userAdmin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String roleStr = req.getParameter("role");
            Role role = (roleStr != null && !roleStr.isBlank()) ? Role.valueOf(roleStr) : null;

            AddAccountRequest addRequest = new AddAccountRequest(
                    username,
                    password,
                    email,
                    role,
                    false // mặc định là chưa bị xoá
            );

            try {
                accountService.register(addRequest);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            HttpUtils.sendRedirect(req, resp, "/admin/users");

        } else if ("edit".equalsIgnoreCase(action)) {
            long userId = Long.parseLong(req.getParameter("userId"));
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String roleStr = req.getParameter("role");
            Role role = (roleStr != null && !roleStr.isBlank()) ? Role.valueOf(roleStr) : null;

            EditAccountRequest editRequest = new EditAccountRequest(
                    userId,
                    username,
                    email,
                    role,
                    false
            );

            accountService.update(editRequest);
            HttpUtils.sendRedirect(req, resp, "/admin/users");

        } else if ("delete".equalsIgnoreCase(action)) {
            long userId = Long.parseLong(req.getParameter("userId"));
            accountService.delete(userId);
            HttpUtils.sendRedirect(req, resp, "/admin/users");
        }
    }
}
