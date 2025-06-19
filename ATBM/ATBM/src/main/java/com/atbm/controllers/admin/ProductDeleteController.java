package com.atbm.controllers.admin;

import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/product-delete")
public class ProductDeleteController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            boolean deleted = productService.deleteProductById(productId);
            if (deleted) {
//                HttpUtils.sendRedirect(req, resp, "/admin/product-list");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xóa sản phẩm");
        }
    }
}
