package com.atbm.controllers.admin;

import com.atbm.config.BaseController;
import com.atbm.models.enums.Role;
import com.atbm.models.wrapper.request.EditAccountRequest;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/users/edit")
public class AccountEditController extends BaseController {
    private AccountService accountService;

    @Override
    public void init() {
        accountService = CDI.current().select(AccountService.class).get();
    }

    // Xử lý POST để update tài khoản
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
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

            boolean updated = accountService.update(editRequest);
            if (!updated) {
                req.setAttribute("error", "Update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Invalid input or error occurred");
        }

        HttpUtils.sendRedirect(req, resp, "/admin/users");
    }
}
