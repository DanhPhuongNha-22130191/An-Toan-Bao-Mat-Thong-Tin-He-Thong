package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.TrendingProductDTO;
import services.HomeService;

@WebServlet("/HomeController")
public class HomeController extends HttpServlet {

    private HomeService homeService;

    @Override
    public void init() throws ServletException {
        super.init();
        homeService = new HomeService();  
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TrendingProductDTO> trendingProducts = homeService.getAllTrendingProducts();

        request.setAttribute("trendingProducts", trendingProducts);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
