package com.atbm.filter;

import com.atbm.models.enums.Role;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        long accountId = Long.parseLong(session.getAttribute("accountId").toString());
        AccountService accountService = new AccountService();
        AccountResponse accountResponse = accountService.getAccountById(accountId);

        if (accountResponse == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy role của user
        Role role = accountResponse.role();
        String path = request.getRequestURI();

        if (path.startsWith(request.getContextPath() + "/admin") && Role.ADMIN != role) {
            response.sendRedirect(request.getContextPath() + "/access-denied");
            return;
        }
        if (path.startsWith(request.getContextPath() + "/user") && Role.ADMIN != role) {
            response.sendRedirect(request.getContextPath() + "/access-denied");
            return;
        }
        request.setAttribute("account", accountResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}