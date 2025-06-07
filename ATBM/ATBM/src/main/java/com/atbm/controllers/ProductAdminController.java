package com.atbm.controllers;

import com.atbm.models.Brand;
import com.atbm.models.Product;
import com.atbm.models.Strap;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/product")
public class ProductAdminController extends HttpServlet {
    private ProductService productService;

    public ProductAdminController() {
        this.productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> product = productService.getAll();
        List<Brand> brands = productService.getAllBrand();
        List<Strap> straps = productService.getAllStrap();

        Map<Long, Brand> brandMap = new HashMap<>();
        for (Brand brand : productService.getAllBrand()) {
            brandMap.put(brand.getBrandId(), brand);
        }

        request.setAttribute("brandMap", brandMap);
        request.setAttribute("product", product);
        request.setAttribute("brands", brands);
        request.setAttribute("straps", straps);
        request.getRequestDispatcher("/views/productAdmin.jsp").forward(request, response);
    }
}
