package com.atbm.controllers.user.info;

import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/info")
public class PersonalInfoController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long accountId = Long.parseLong(session.getAttribute("accountId").toString());
        try {
            AccountResponse account = accountService.getUserInfo(accountId);
            HttpUtils.setAttribute(req, "account", account);
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }
}