package com.atbm.controllers.admin;

import com.atbm.models.entity.Order;
import com.atbm.models.enums.OrderStatus;
import com.atbm.services.OrderService;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order")
public class OrderController extends HttpServlet {
    private OrderService orderService;

    @Inject
    public void init() throws ServletException {
        orderService = CDI.current().select(OrderService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.getOrders();
        HttpUtils.setAttribute(req, "orders", orders);
        HttpUtils.dispatcher(req, resp, "/views/orderAdmin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdStr = req.getParameter("orderId");
        String newStatusStr = req.getParameter("status");
        long orderId = Long.parseLong(orderIdStr);

        OrderStatus newStatus = OrderStatus.valueOf(newStatusStr);

        orderService.updateStatus(orderId, newStatus);
        resp.sendRedirect(req.getContextPath() + "/admin/order");
    }

}
