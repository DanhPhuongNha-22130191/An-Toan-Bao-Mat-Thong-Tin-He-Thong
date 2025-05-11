package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Brand;
import dto.Strap;
import models.Product;
import services.ProductService;

@WebServlet("/CategoryController")
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

        List<Brand> brands = productService.getAllBrand();
        List<Strap> straps = productService.getAllStrap();
        List<Product> products;

        if (brandIdParam != null && !brandIdParam.isEmpty()) {
            int brandId = Integer.parseInt(brandIdParam);
            products = productService.getByBrandId(brandId); 
            System.out.println(brandId);
        } else {
            products = productService.getAll();
        }

        request.setAttribute("brands", brands);
        request.setAttribute("straps", straps);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/views/category.jsp").forward(request, response);
    }

}
