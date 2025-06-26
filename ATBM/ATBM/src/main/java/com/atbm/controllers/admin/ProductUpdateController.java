package com.atbm.controllers.admin;

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

@WebServlet("/admin/product/update")
@MultipartConfig
public class ProductUpdateController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        productService.editProduct(editRequest);
        HttpUtils.sendRedirect(req, resp, "/admin/product");
    }
}
