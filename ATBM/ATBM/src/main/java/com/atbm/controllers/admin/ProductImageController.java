package com.atbm.controllers.admin;

import com.atbm.models.wrapper.response.ProductResponse;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/product-image/*")
public class ProductImageController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = CDI.current().select(ProductService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdStr = HttpUtils.getPathInfo(req);
        if (productIdStr == null) {
            return;
        }
        try {
            long productId = Long.parseLong(productIdStr);
            ProductResponse product = productService.getProductById(productId);
            byte[] imageData = product.getImage();
            HttpUtils.setResponseImage(resp, imageData);
        } catch (NumberFormatException e) {

        }
    }
}
