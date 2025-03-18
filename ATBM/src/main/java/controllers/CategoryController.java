package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Brand;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Brand> brands = null;
        List<Product> trendingProducts = null;
        try {
            brands = productService.getAllBrand();
            
            String brandId = request.getParameter("brandId");
            System.out.println(brandId); 

            if (brandId != null && !brandId.isEmpty()) {
                trendingProducts = productService.getProductsByBrand(brandId);
            } else {
                trendingProducts = productService.getAll();
            }

            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                request.setAttribute("trendingProducts", trendingProducts);
                request.getRequestDispatcher("/views/category.jsp").include(request, response);
            } else {
                request.setAttribute("brands", brands);
                request.setAttribute("trendingProducts", trendingProducts);
                request.getRequestDispatcher("/views/category.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            request.getRequestDispatcher("/views/category.jsp").forward(request, response);  // Error handling on category.jsp
        }
    }
}
