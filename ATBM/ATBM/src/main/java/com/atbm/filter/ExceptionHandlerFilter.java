package com.atbm.filter;

import com.atbm.utils.JsonUtils;
import com.atbm.utils.LogUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            // Ghi log chi tiết lỗi với errorId
            String errorId = java.util.UUID.randomUUID().toString();
            LogUtils.error(ExceptionHandlerFilter.class, "Error ID: " + errorId + ", URI: " + request.getRequestURI() + ", Message: " + e.getMessage(), e);

            // Xử lý các loại ngoại lệ khác nhau
            String errorMessage;
            int statusCode;

            if (e instanceof ServletException) {
                errorMessage = "Lỗi servlet: " + e.getMessage();
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500
            } else if (e instanceof IOException) {
                errorMessage = "Lỗi I/O: " + e.getMessage();
                statusCode = HttpServletResponse.SC_BAD_REQUEST; // 400
            } else {
                errorMessage = "Lỗi không xác định: " + e.getMessage();
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500
            }

            // Tạo phản hồi JSON bằng JsonUtils
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorId", errorId);
            errorResponse.put("status", statusCode);
            errorResponse.put("message", errorMessage);
            errorResponse.put("path", request.getRequestURI());
            errorResponse.put("timestamp", LocalDateTime.now().toString());

            response.setStatus(statusCode);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(JsonUtils.toJson(errorResponse));
            out.flush();
        }
    }
}