package com.atbm.controllers;

import com.atbm.models.Product;
import com.atbm.services.ProductService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product/filter")
public class CategoryAjaxController extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String brandIdParam = request.getParameter("brandId");
        String strapIdParam = request.getParameter("strapId");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");

        Integer brandId = (brandIdParam != null && !brandIdParam.isEmpty()) ? Integer.parseInt(brandIdParam) : null;
        Integer strapId = (strapIdParam != null && !strapIdParam.isEmpty()) ? Integer.parseInt(strapIdParam) : null;
        Double minPrice = (minPriceParam != null && !minPriceParam.isEmpty()) ? Double.parseDouble(minPriceParam) : null;
        Double maxPrice = (maxPriceParam != null && !maxPriceParam.isEmpty()) ? Double.parseDouble(maxPriceParam) : null;

        List<Product> products = productService.filterProducts(brandId, strapId, minPrice, maxPrice);
        for(Product product : products) {
            System.out.println(product);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(products));
    }
}
