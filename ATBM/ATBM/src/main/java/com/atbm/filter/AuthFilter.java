//package com.atbm.filter;
//
//import com.atbm.dto.AccountDTO;
//import com.atbm.models.Account;
//import com.atbm.services.AccountService;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//
//@WebFilter("/user/*")
//public class AuthFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        HttpSession session = req.getSession();
//        long userId = 1;
//        Account account = new AccountService().getById(userId);
//        AccountDTO dto = new AccountDTO(account.getAccountId(), account.getUsername(), account.getPassword());
//        session.setAttribute("user", dto);
//        if (session == null || session.getAttribute("user") == null) {
//            res.sendRedirect("/ATBM/views/login.jsp");
//        } else {
//            filterChain.doFilter(req, res);
//        }
//    }
//}
