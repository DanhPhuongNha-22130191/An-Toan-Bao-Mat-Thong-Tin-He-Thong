package com.atbm.controllers;

import com.atbm.models.Product;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/HomeController")
public class HomeController extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> trendingProducts = productService.getAllTrendingProduct();
        List<Product> newProducts = productService.getAll();

        // Kiểm tra xem có dữ liệu không
//        System.out.println("Số lượng sản phẩm trending: " + trendingProducts.size());


        request.setAttribute("trendingProducts", trendingProducts);
        request.setAttribute("newProducts", newProducts);

        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }


}
