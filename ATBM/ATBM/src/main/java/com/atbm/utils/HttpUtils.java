package com.atbm.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HttpUtils {
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        request.setAttribute(key, value);
    }

    public static Object getAttribute(HttpServletRequest request, String key) {
        return request.getAttribute(key);
    }

    public static void removeAttribute(HttpServletRequest request, String key) {
        request.removeAttribute(key);
    }

    public static void dispatcher(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(path).forward(request, response);
    }
}