package com.atbm.controllers;

import com.atbm.models.Order;
import com.atbm.models.OrderSecurity;
import com.atbm.services.AccountService;
import com.atbm.services.CartService;
import com.atbm.services.OrderService;
import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@WebServlet("/user/order/confirmation/*")
public class ConfirmationController extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;
    private VoucherService voucherService;
    private AccountService accountService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
        orderService = new OrderService();
        cartService = new CartService();
        voucherService = new VoucherService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long orderId = null;
            try {
                orderId = Long.parseLong(req.getPathInfo().substring(1));
            } catch (Exception e) {
                req.setAttribute("message", "Không tìm thấy đơn hàng!");
                req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
                return;
            }
            Order order = orderService.getById(orderId);
            if (order == null) {
                req.setAttribute("message", "Đơn hàng không tồn tại!");
                req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
                return;
            }
            OrderSecurity orderSecurity = orderService.getSecurity(orderId);
            order.setCartDTO(cartService.convertToDTO(orderId));
            if(order.getCartDTO() != null && order.getVoucherId() != null)
                order.getCartDTO().setVoucher(voucherService.getById(order.getVoucherId()));

            req.setAttribute("order", order);
            req.setAttribute("orderSecurity", orderSecurity);
            req.setAttribute("orderDetail", order.getOrderDetail());
            req.setAttribute("voucher", (order.getCartDTO() != null) ? order.getCartDTO().getVoucher() : null);
            req.setAttribute("cartDTO", order.getCartDTO());
            req.setAttribute("dateFormatter", DATE_FORMATTER);
            req.getRequestDispatcher("/views/confirmation.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Có lỗi xảy ra: " + e.toString());
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
        }
    }
}