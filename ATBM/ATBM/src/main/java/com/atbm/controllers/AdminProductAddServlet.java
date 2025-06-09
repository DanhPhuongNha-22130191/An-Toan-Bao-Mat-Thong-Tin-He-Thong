package com.atbm.controllers;

import com.atbm.models.Product;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Paths;

@WebServlet("/admin/addProduct")
@MultipartConfig
public class AdminProductAddServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private static final String IMAGE_UPLOAD_DIR = "D:/ATBM-Uploads/product-images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String status = request.getParameter("status");

            if (name == null || name.isBlank() || description == null || status == null) {
                throw new IllegalArgumentException("Thiếu thông tin sản phẩm bắt buộc.");
            }

            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            long brandId = Long.parseLong(request.getParameter("brandId"));
            long strapId = Long.parseLong(request.getParameter("strapId"));
            double size = Double.parseDouble(request.getParameter("size"));
            boolean haveTrending = Boolean.parseBoolean(request.getParameter("haveTrending"));
            boolean waterResistance = Boolean.parseBoolean(request.getParameter("waterResistance"));

            Part imagePart = request.getPart("image");
            byte[] imageBytes = null;

            if (imagePart != null && imagePart.getSize() > 0) {
                try (InputStream inputStream = imagePart.getInputStream()) {
                    imageBytes = inputStream.readAllBytes();
                }
            }

            Product product = new Product(
                    0, name, price, description, stock, imageBytes,

                    haveTrending, size, waterResistance,
                    brandId, strapId, status, false
            );

            productService.insert(product);

            response.sendRedirect("/ATBM/admin/product");

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không thể thêm sản phẩm: " + e.getMessage());
        }
    }
}
