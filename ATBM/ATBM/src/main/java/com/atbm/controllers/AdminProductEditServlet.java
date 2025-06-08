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
import java.nio.file.Paths;

@WebServlet("/admin/editProduct")
@MultipartConfig
public class AdminProductEditServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private static final String IMAGE_UPLOAD_DIR = "D:/ATBM-Uploads/product-images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        long id = Long.parseLong(request.getParameter("productId"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        int stock = Integer.parseInt(request.getParameter("stock"));
        long brandId = Long.parseLong(request.getParameter("brandId"));
        String status = request.getParameter("status");

        Part imagePart = request.getPart("image");
        String imageFileName;

        if (imagePart != null && imagePart.getSize() > 0) {
            imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

            File uploadDir = new File(IMAGE_UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String imageFullPath = IMAGE_UPLOAD_DIR + File.separator + imageFileName;
            imagePart.write(imageFullPath);
        } else {
            imageFileName = productService.getImageByProductId(id);
        }

        Product product = new Product(id, name, price, description, stock, imageFileName, brandId, status);
        productService.update(product);

        response.sendRedirect("/ATBM/admin/product");
    }
}
