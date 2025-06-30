package com.atbm.controllers.order;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.OrderService;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/order")
public class OrderController extends BaseController {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = CDI.current().select(OrderService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = getAccountId(req);
        List<OrderResponse> orders = orderService.getOrdersByAccountId(id);
        req.setAttribute("orders", orders);
    }

}
