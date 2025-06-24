package com.atbm.utils;


import jakarta.servlet.http.HttpServletRequest;

public class UrlUtils {

    /**
     * Lấy phần tử trong URI theo vị trí chỉ định.
     * @param request HttpServletRequest
     * @param index vị trí (0-based), sau phần domain và context path
     * @return chuỗi tương ứng hoặc null nếu không có
     */
    public static String getPathSegment(HttpServletRequest request, int index) {
        String uri = request.getRequestURI();              // /your-app/order/detail/123
        String context = request.getContextPath();         // /your-app
        String relativePath = uri.substring(context.length()); // /order/detail/123
        String[] segments = relativePath.split("/");

        // segments[0] sẽ là "" vì bắt đầu bằng /
        int actualIndex = index + 1;
        if (actualIndex >= 0 && actualIndex < segments.length) {
            return segments[actualIndex];
        }
        return null;
    }

    /**
     * Lấy phần cuối cùng trong đường dẫn URI (thường là id).
     */
    public static String getLastPathSegment(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String relativePath = uri.substring(context.length());
        String[] segments = relativePath.split("/");
        return segments.length > 0 ? segments[segments.length - 1] : null;
    }

    /**
     * Lấy phần path sau một prefix xác định.
     * Ví dụ: URI = /order/detail/123 → getPathAfter("/order/detail") = "123"
     */
    public static String getPathAfter(HttpServletRequest request, String prefix) {
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String relativePath = uri.substring(context.length());
        if (relativePath.startsWith(prefix)) {
            return relativePath.substring(prefix.length()).replaceFirst("^/", "");
        }
        return null;
    }
}
