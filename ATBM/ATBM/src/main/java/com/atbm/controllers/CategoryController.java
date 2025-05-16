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
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String brandIdParam = request.getParameter("brandId");
        String strapIdParam = request.getParameter("strapId");
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");

        List<Brand> brands = productService.getAllBrand();
        List<Strap> straps = productService.getAllStrap();
        List<Product> products;

        Integer brandId = brandIdParam != null && !brandIdParam.isEmpty() ? Integer.parseInt(brandIdParam) : null;
        Integer strapId = strapIdParam != null && !strapIdParam.isEmpty() ? Integer.parseInt(strapIdParam) : null;
        Double minPrice = minPriceParam != null && !minPriceParam.isEmpty() ? Double.parseDouble(minPriceParam) : null;
        Double maxPrice = maxPriceParam != null && !maxPriceParam.isEmpty() ? Double.parseDouble(maxPriceParam) : null;

        products = productService.filterProducts(brandId, strapId, minPrice, maxPrice);

        request.setAttribute("brands", brands);
        request.setAttribute("straps", straps);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/views/category.jsp").forward(request, response);
    }


}
