package com.atbm.utils;

import com.google.gson.Gson;
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

    public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + path);
    }

    public static String getPathInfo(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            return pathInfo.substring(1);
        } else {
            return null;
        }
    }

    public static void setResponseJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(data));
    }

    public static void setResponseImage(HttpServletResponse response, byte[] data) throws IOException {
        response.setContentType("image/png");
        response.setCharacterEncoding("UTF-8");
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
    }



}
