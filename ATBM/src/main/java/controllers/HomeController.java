package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Product;
import services.ProductService;

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
