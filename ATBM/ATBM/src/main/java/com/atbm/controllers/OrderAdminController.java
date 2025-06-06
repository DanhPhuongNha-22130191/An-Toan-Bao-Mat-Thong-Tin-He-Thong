package com.atbm.controllers;

import com.atbm.models.Order;
import com.atbm.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order")
public class OrderAdminController extends HttpServlet {
    private static final OrderService orderService = new OrderService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderService.getAll();
        for (Order order : orders) {
            System.out.println(order);
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/orderAdmin.jsp").forward(request, response);
    }
}
