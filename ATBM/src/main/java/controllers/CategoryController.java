//package controllers;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import dto.TrendingProductDTO;
//import models.Product;
//import services.HomeService;
//import services.ProductService;
//
//@WebServlet("/CategoryController")
//public class CategoryController extends HttpServlet {
//
//	 private HomeService homeService;
//	    private ProductService productService;
//
//	    @Override
//	    public void init() throws ServletException {
//	        super.init();
//	        homeService = new HomeService();  
//	       
//	    }
//
//	    @Override
//	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        List<TrendingProductDTO> trendingProducts = homeService.getAllTrendingProducts();
//
//	        // Kiểm tra xem có dữ liệu không
////	        System.out.println("Số lượng sản phẩm trending: " + trendingProducts.size());
//
//
//	        request.setAttribute("trendingProducts", trendingProducts);
//
//	        request.getRequestDispatcher("/views/category.jsp").forward(request, response);
//	    }
//
//
//}
