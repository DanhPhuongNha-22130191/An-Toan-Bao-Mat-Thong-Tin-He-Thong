package com.atbm.controllers.cart;

import com.atbm.models.wrapper.request.AddCartRequest;
import com.atbm.models.wrapper.request.UpdateCartRequest;
import com.atbm.models.wrapper.response.CartResponse;
import com.atbm.services.CartService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/cart")
public class CartController extends HttpServlet {
    private final static String VIEW_CART = "/WEB-INF/views/cart.jsp";
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = (long) req.getSession().getAttribute("accountId");
        CartResponse response = cartService.getCartByUserId(accountId);
        HttpUtils.setAttribute(req, "cart", response);
//        HttpUtils.dispatcher(req, resp, VIEW_CART);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = (long) req.getSession().getAttribute("accountId");
        String cartItemIdStr = req.getParameter("cartItemId");
        String quantityStr = req.getParameter("quantity");
        try {
            long cartItemId = Long.parseLong(cartItemIdStr);
            int quantity = Integer.parseInt(quantityStr);
            cartService.updateQuantity(accountId, new UpdateCartRequest(cartItemId, quantity));
            doGet(req, resp);
        } catch (NumberFormatException e) {
            LogUtils.debug(CartController.class, e.getMessage());
            throw new RuntimeException("Dữ liệu thiếu");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = (long) req.getSession().getAttribute("accountId");
        String cartItemIdStr = req.getParameter("cartItemId");
        String quantityStr = req.getParameter("quantity");
        try {
            long cartItemId = Long.parseLong(cartItemIdStr);
            int quantity = Integer.parseInt(quantityStr);
            cartService.removeProductFromCart(accountId, new UpdateCartRequest(cartItemId, quantity));
            doGet(req, resp);
        } catch (NumberFormatException e) {
            LogUtils.debug(CartController.class, e.getMessage());
            throw new RuntimeException("Dữ liệu thiếu");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = (long) req.getSession().getAttribute("accountId");
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");
        try {
            long productId = Long.parseLong(productIdStr);
            int quantity = Integer.parseInt(quantityStr);
            cartService.addProductToCart(accountId, new AddCartRequest(productId, quantity));
            doGet(req, resp);
        } catch (NumberFormatException e) {
            LogUtils.debug(CartController.class, e.getMessage());
            throw new RuntimeException("Dữ liệu thiếu");
        }
    }
}
