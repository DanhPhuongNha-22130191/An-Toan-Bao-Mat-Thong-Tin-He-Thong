package com.atbm.controllers.shop;

import com.atbm.models.wrapper.response.ProductResponse;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = CDI.current().select(ProductService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductResponse> products = productService.getProducts();
        HttpUtils.setAttribute(req, "newProducts", products);
        HttpUtils.setAttribute(req, "trendingProducts", products.subList(0, 5));
        HttpUtils.dispatcher(req, resp, "/views/index.jsp");
    }
}