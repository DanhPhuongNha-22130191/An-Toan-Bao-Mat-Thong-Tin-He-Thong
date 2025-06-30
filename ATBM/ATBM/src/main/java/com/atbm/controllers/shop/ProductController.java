package com.atbm.controllers.shop;

import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Strap;
import com.atbm.models.wrapper.request.FilterProductRequest;
import com.atbm.models.wrapper.response.ProductResponse;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/shop/product")
public class ProductController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = CDI.current().select(ProductService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductResponse> products = productService.getProducts();
        List<Brand> brands = productService.getBrands();
        List<Strap> straps = productService.getStraps();
        double minPrice = productService.getMinPrice();
        double maxPrice = productService.getMaxPrice();

        HttpUtils.setAttribute(req, "brands", brands);
        HttpUtils.setAttribute(req, "straps", straps);
        HttpUtils.setAttribute(req, "minPrice", minPrice);
        HttpUtils.setAttribute(req, "maxPrice", maxPrice);
        HttpUtils.setAttribute(req, "products", products);

        HttpUtils.dispatcher(req, resp, "/views/category.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterProductRequest filterProductRequest = new FilterProductRequest(
                parseIdSet(req.getParameterValues("brandId[]")),
                parseIdSet(req.getParameterValues("strapId[]")),
                parseDouble(req.getParameter("minPrice")),
                parseDouble(req.getParameter("maxPrice"))
        );
        List<ProductResponse> products = productService.filterProduct(filterProductRequest);
        HttpUtils.setResponseJson(resp, products);
    }

    private Set<Long> parseIdSet(String[] arr) {
        if (arr == null || arr.length == 0) return new HashSet<>();
        return java.util.Arrays.stream(arr)
                .filter(s -> s != null && !s.isEmpty())
                .map(Long::parseLong)
                .collect(java.util.stream.Collectors.toSet());

    }

    private double parseDouble(String str) {
        return (str == null || str.isEmpty()) ? 0 : Double.parseDouble(str);
    }
}
