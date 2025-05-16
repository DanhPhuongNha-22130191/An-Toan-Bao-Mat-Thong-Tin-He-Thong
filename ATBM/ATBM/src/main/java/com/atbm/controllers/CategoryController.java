package com.atbm.controllers;

import com.atbm.dto.Brand;
import com.atbm.dto.Strap;
import com.atbm.models.Product;
import com.atbm.services.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product/category")
public class CategoryController extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Double minPrice = productService.getMinProductPrice();
        Double maxPrice = productService.getMaxProductPrice();
        List<Brand> brands = productService.getAllBrand();
        List<Strap> straps = productService.getAllStrap();
        List<Product> products = productService.filterProducts(null, null, null, null); // default

        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("brands", brands);
        request.setAttribute("straps", straps);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/views/category.jsp").forward(request, response);
    }
}
