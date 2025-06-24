package com.atbm.controllers.order;

import com.atbm.models.entity.Order;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.OrderService;
import com.atbm.utils.UrlUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/user/order/*")
public class OrderDetailController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = CDI.current().select(OrderService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        long orderId = getOrderId(req);
        OrderResponse order = orderService.getOrderById(orderId, id);
        req.setAttribute("order", order);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        long orderId = getOrderId(req);
        String signature = req.getParameter("signature");
        orderService.updateSignature(id, orderId, signature);
        doGet(req, resp);
    }

    private long getOrderId(HttpServletRequest req) {
        try {
            return Long.parseLong(Objects.requireNonNull(UrlUtils.getLastPathSegment(req)));
        } catch (NumberFormatException | NullPointerException e) {
            throw new RuntimeException("Đường dẫn không hợp lệ");
        }
    }
}
