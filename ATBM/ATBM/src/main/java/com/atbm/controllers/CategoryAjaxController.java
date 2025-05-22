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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/product/filter")
public class CategoryAjaxController extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] brandIdParams = request.getParameterValues("brandId[]");
        String[] strapIdParams = request.getParameterValues("strapId[]");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");

        List<Integer> brandIds = brandIdParams != null ?
                Arrays.stream(brandIdParams)
                        .filter(param -> !param.isEmpty())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()) :
                null;

        List<Integer> strapIds = strapIdParams != null ?
                Arrays.stream(strapIdParams)
                        .filter(param -> !param.isEmpty())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()) :
                null;

        Double minPrice = (minPriceParam != null && !minPriceParam.isEmpty()) ? Double.parseDouble(minPriceParam) : null;
        Double maxPrice = (maxPriceParam != null && !maxPriceParam.isEmpty()) ? Double.parseDouble(maxPriceParam) : null;

        List<Product> products = productService.filterProducts(brandIds, strapIds, minPrice, maxPrice);
        for (Product product : products) {
            System.out.println(product);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(products));
    }
}
