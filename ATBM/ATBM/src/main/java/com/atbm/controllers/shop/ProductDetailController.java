package com.atbm.controllers.shop;

import com.atbm.models.wrapper.response.ProductResponse;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/shop/product/*")
public class ProductDetailController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdStr = HttpUtils.getPathInfo(req);
        if (productIdStr != null) {
            try {
                long productId = Long.parseLong(productIdStr);
                ProductResponse productResponse = productService.getProductById(productId);
                HttpUtils.setAttribute(req, "product", productResponse);
//        HttpUtils.dispatcher(req, resp, "/WEB-INF/views/shop/product.jsp");
            } catch (NumberFormatException e) {
                LogUtils.debug(ProductDetailController.class, e.getMessage());
                throw new RuntimeException("Trang không tồn tại");
            }
        }
    }
}
