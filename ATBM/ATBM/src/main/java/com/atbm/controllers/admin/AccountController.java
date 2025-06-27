package com.atbm.controllers.admin;

import com.atbm.models.enums.Role;
import com.atbm.models.wrapper.request.AddAccountRequest;
import com.atbm.models.wrapper.request.EditAccountRequest;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/users")
public class AccountController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() {
        accountService = CDI.current().select(AccountService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var accounts = accountService.getAccounts();
        HttpUtils.setAttribute(req, "accounts", accounts);
        HttpUtils.dispatcher(req, resp, "/WEB-INF/views/userAdmin.jsp");
    }

    // Thêm tài khoản mới
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                false
        );

        accountService.register(addRequest);
        HttpUtils.sendRedirect(req, resp, "/admin/users");
    }

    // Cập nhật tài khoản (Edit)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    }

    // Xoá tài khoản (Delete - đánh dấu isDeleted = true)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = Long.parseLong(req.getParameter("userId"));
        accountService.delete(userId);
        HttpUtils.sendRedirect(req, resp, "/admin/users");
    }
}
