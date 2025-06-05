//package com.atbm.controllers;
//
//import com.atbm.models.Product;
//import com.atbm.services.ProductService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//
//@WebServlet("/admin/product/action")
//public class ProductActionController extends HttpServlet {
//    private final ProductService productService;
//
//    public ProductActionController() {
//        this.productService = new ProductService();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//
//        if (action == null) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter");
//            return;
//        }
//
//        switch (action) {
//            case "add":
//                handleAddProduct(request, response);
//                break;
//            case "edit":
//                handleEditProduct(request, response);
//                break;
//            case "delete":
//                handleDeleteProduct(request, response);
//                break;
//            default:
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action parameter");
//        }
//    }
//
//    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String name = request.getParameter("productName");
//        String category = request.getParameter("category");
//        String description = request.getParameter("description");
//        String image = request.getParameter("image");
//        double price = Double.parseDouble(request.getParameter("price"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//
//        Product product = new Product();
//        product.setName(name);
//        product.setCategory(category);
//        product.setDescription(description);
//        product.setImage(image);
//        product.setPrice(price);
//        product.setStock(quantity);
//
//        boolean result = productService.addProduct(product);
//
//        if (result) {
//            response.sendRedirect(request.getContextPath() + "/admin/product");
//        } else {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add product");
//        }
//    }
//
//    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        long id = Long.parseLong(request.getParameter("productId"));
//        String name = request.getParameter("productName");
//        String category = request.getParameter("category");
//        String description = request.getParameter("description");
//        String image = request.getParameter("image");
//        double price = Double.parseDouble(request.getParameter("price"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//
//        Product product = new Product();
//        product.setProductId(id);
//        product.setName(name);
//        product.setCategory(category);
//        product.setDescription(description);
//        product.setImage(image);
//        product.setPrice(price);
//        product.setStock(quantity);
//
//        boolean result = productService.updateProduct(product);
//
//        if (result) {
//            response.sendRedirect(request.getContextPath() + "/admin/product");
//        } else {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update product");
//        }
//    }
//
//    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        long id = Long.parseLong(request.getParameter("productId"));
//
//        boolean result = productService.deleteProduct(id);
//
//        if (result) {
//            response.sendRedirect(request.getContextPath() + "/admin/product");
//        } else {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete product");
//        }
//    }
//}
