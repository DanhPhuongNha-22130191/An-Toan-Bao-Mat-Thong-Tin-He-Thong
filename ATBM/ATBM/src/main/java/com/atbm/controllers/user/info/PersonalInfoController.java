package com.atbm.controllers.user.info;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/info")
public class PersonalInfoController extends BaseController {
    @Inject
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long accountId = getAccountId(req);
            AccountResponse account = accountService.getUserInfo(accountId);
            HttpUtils.setAttribute(req, "account", account);
            HttpUtils.setAttribute(req, "activeTab", "profile");
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }
}