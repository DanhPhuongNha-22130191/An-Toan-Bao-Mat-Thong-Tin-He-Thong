package com.atbm.controllers.order;

import com.atbm.mapper.FormMapper;
import com.atbm.models.wrapper.request.CheckoutOrderRequest;
import com.atbm.models.wrapper.response.CartResponse;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.OrderService;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/order")
public class OrderController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = CDI.current().select(OrderService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("accountId"));
        List<OrderResponse> orders = orderService.getOrdersByAccountId(id);
        req.setAttribute("orders", orders);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("accountId"));
        CartResponse cart = getCartInSession(req.getSession());
        CheckoutOrderRequest checkoutOrderRequest = FormMapper.bind(req.getParameterMap(), CheckoutOrderRequest.class);
        orderService.checkout(id, checkoutOrderRequest, cart.getUpdateAt());
    }

    private CartResponse getCartInSession(HttpSession session) {
        try {
            return (CartResponse) session.getAttribute("cart");
        } catch (Exception e) {
            //chuyển trang về cart
            throw e;
        }
    }
}
