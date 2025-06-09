package com.atbm.controllers;

import com.atbm.models.Product;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/productImage")
public class ProductImageServlet extends HttpServlet {
    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long productId;
        try {
            productId = Long.parseLong(productIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Product product = productService.getById(productId);
        if (product == null || product.getImage() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] imageData = product.getImage();

        // Cài đặt content type (ví dụ: image/jpeg)
        response.setContentType("image/jpeg");
        response.setContentLength(imageData.length);

        // Gửi dữ liệu ảnh về client
        response.getOutputStream().write(imageData);
    }
}
