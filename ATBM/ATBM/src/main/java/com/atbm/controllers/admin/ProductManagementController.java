package com.atbm.controllers.admin;

import com.atbm.models.entity.Product;
import com.atbm.models.wrapper.response.ProductResponse;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/product")
public class ProductManagementController extends HttpServlet{
    private ProductService productService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductResponse> listProducts = productService.getProducts();
//        for (ProductResponse productResponse : listProducts) {
//            System.out.println(productResponse);
//        }
        HttpUtils.setAttribute(req, "products", listProducts);
//        HttpUtils.dispatcher(req, resp, "/WEB-INF/views/admin/product-management.jsp");
    }
}
