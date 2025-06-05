package com.atbm.controllers;

import com.atbm.models.Brand;
import com.atbm.models.Product;
import com.atbm.models.ProductState;
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

        Map<Long, Brand> brandMap = new HashMap<>();
        for (Brand brand : productService.getAllBrand()) {
            brandMap.put(brand.getBrandId(), brand);
        }

        Map<Long, ProductState> stateMap = new HashMap<>();
        for (ProductState state : productService.getAllState()) {
            stateMap.put(state.getStateId(), state);
        }

        List<ProductState> states = productService.getAllState();

        List<Brand> brands = productService.getAllBrand();

        int totalProductStock = productService.getTotalProductStock();

        int countLowStockProducts = productService.countLowStockProducts();

        int totalBrandCount = productService.getTotalBrandCount();

        request.setAttribute("brandMap", brandMap);
        request.setAttribute("product", product);
        request.setAttribute("stateMap", stateMap);
        request.setAttribute("states", states);
        request.setAttribute("brands", brands);
        request.setAttribute("totalProductStock", totalProductStock);
        request.setAttribute("countLowStockProducts", countLowStockProducts);
        request.setAttribute("totalBrandCount", totalBrandCount);
        request.getRequestDispatcher("/views/productAmin.jsp").forward(request, response);
    }
}
