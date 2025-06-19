package com.atbm.controllers.admin.product;

import com.atbm.models.wrapper.request.EditProductRequest;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/admin/product-edit")
@MultipartConfig
public class ProductEditController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            long brandId = Long.parseLong(req.getParameter("brandId"));

            byte[] imageBytes = null;
            Part imagePart = req.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                try (InputStream is = imagePart.getInputStream()) {
                    imageBytes = is.readAllBytes();
                }
            }

            EditProductRequest editRequest = new EditProductRequest(
                    productId, name, price, description, stock, imageBytes, brandId
            );

            boolean updated = productService.editProduct(editRequest);

            if (updated) {
//                HttpUtils.dispatcher(req, resp, "/admin/product-list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpUtils.setAttribute(req, "Lỗi cập nhật sản phẩm", e.getMessage());
//            HttpUtils.dispatcher(req, resp, "/WEB-INF/views/admin/product-edit.jsp");
        }
    }
}
