//package com.atbm.filter;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//
///**
// * Filter dùng để xác thực người dùng hoặc admin
// */
//public class AuthenticationFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpSession session = request.getSession(true);
//        if (session == null || session.getAttribute("accountId") == null) {
//            response.sendRedirect(request.getContextPath() + "/login");
//        } else {
//            try {
//                Long.parseLong(session.getAttribute("accountId").toString());
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/login");
//                return;
//            }
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }
//}
