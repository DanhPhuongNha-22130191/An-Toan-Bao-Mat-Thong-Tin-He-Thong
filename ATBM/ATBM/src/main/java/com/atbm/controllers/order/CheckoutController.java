package com.atbm.controllers.order;

import com.atbm.config.BaseController;
import com.atbm.controllers.cart.CartController;
import com.atbm.mapper.FormMapper;
import com.atbm.models.wrapper.request.CheckoutOrderRequest;
import com.atbm.models.wrapper.response.CartResponse;
import com.atbm.services.OrderService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/checkout")
public class CheckoutController extends BaseController {
    public final String CHECKOUT_URL = "/views/checkout.jsp";
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = CDI.current().select(OrderService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpUtils.dispatcher(req, resp, CHECKOUT_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = getAccountId(req);
        CartResponse cart = getCartInSession(req.getSession());
        if (cart == null) {
            HttpUtils.sendRedirect(req, resp, CartController.CART_URL);
            return;
        }
        CheckoutOrderRequest checkoutOrderRequest = FormMapper.bind(req.getParameterMap(), CheckoutOrderRequest.class);
        long orderId = orderService.checkout(id, checkoutOrderRequest, cart.getUpdateAt());
        HttpUtils.sendRedirect(req, resp, OrderDetailController.ORDER_DETAIL_URL +orderId);
    }

    private CartResponse getCartInSession(HttpSession session) {
        try {
            return (CartResponse) session.getAttribute("cart");
        } catch (Exception e) {
            return null;
        }
    }
}
