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

    // Hiển thị danh sách user
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Account> accounts = accountService.getAccounts();
        HttpUtils.setAttribute(req, "accounts", accounts);
        HttpUtils.dispatcher(req, resp, "/views/userAdmin.jsp");
    }

    // Thêm user mới (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String roleStr = req.getParameter("role");
        Role role = (roleStr != null && !roleStr.isBlank()) ? Role.valueOf(roleStr) : null;

        AddAccountRequest addRequest = new AddAccountRequest(username, password, email, role, false);
        accountService.register(addRequest);

        HttpUtils.sendRedirect(req, resp, "/admin/users");
    }

    // Xóa user (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = Long.parseLong(req.getParameter("userId"));
        accountService.delete(userId);
        HttpUtils.sendRedirect(req, resp, "/admin/users");
    }
}
