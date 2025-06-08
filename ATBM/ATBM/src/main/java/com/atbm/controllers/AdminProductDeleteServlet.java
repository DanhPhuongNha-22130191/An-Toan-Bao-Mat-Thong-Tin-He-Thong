package com.atbm.controllers;

import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/deleteProduct")
public class AdminProductDeleteServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long productId = Long.parseLong(request.getParameter("id"));
            productService.deleteById(productId);
        } catch (NumberFormatException e) {
        }

        response.sendRedirect("/ATBM/admin/product");
    }
}
