package com.atbm.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/user/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        if (!uri.equals("/ATBM/user/account")) {
            if (session == null || session.getAttribute("user") == null) {
                res.sendRedirect("/ATBM/views/login.jsp");
                return;
            }
        }
        filterChain.doFilter(req, res);
    }
}
