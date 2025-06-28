package com.atbm.controllers.admin;

import com.atbm.models.entity.Order;
import com.atbm.services.OrderService;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order")
public class OrderController extends HttpServlet {
//    private OrderService orderService;
//
//    @Override
//    public void init() throws ServletException {
//        orderService = new OrderService();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<Order> listOrders = orderService.getOrders();
//        HttpUtils.setAttribute(req, "orders", listOrders);
//        HttpUtils.dispatcher(req, resp, "/WEB-INF/views/orderAdmin.jsp");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String orderIdStr = req.getParameter("orderId");
//        String newStatus = req.getParameter("status");
//        long orderId = Long.parseLong(orderIdStr);
//        orderService.updateStatus(orderId, newStatus);
//        HttpUtils.dispatcher(req, resp, "/WEB-INF/views/orderAdmin.jsp");
//    }
}
