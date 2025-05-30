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
    private Long accountId;
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
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Không tìm thấy đơn hàng");
                req.getRequestDispatcher("/views/confirmation.jsp").forward(req, resp);
            }
            Order order = orderService.getById(orderId);
            OrderSecurity orderSecurity= orderService.getSecurity(orderId);
            accountId = order.getAccountId();
            order.setCartDTO(cartService.convertToDTO(orderId));
            order.getCartDTO().setVoucher(voucherService.getById(order.getVoucherId()));

            // Lấy thông tin chi tiết đơn hàng
            req.setAttribute("order", order);
            req.setAttribute("orderSecurity", orderSecurity);
            req.setAttribute("orderDetail", order.getOrderDetail());
            req.setAttribute("voucher", order.getCartDTO().getVoucher());
            req.setAttribute("cartDTO", order.getCartDTO());
            req.setAttribute("dateFormatter", DATE_FORMATTER);
            req.getRequestDispatcher("/views/confirmation.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long orderId = null;
            String signature = null;
            try {
                orderId = Long.parseLong(req.getParameter("orderId"));
                signature = req.getParameter("signature");
                if (signature == null) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Không tìm thấy đơn hàng");
                req.getRequestDispatcher("/views/confirmation.jsp").forward(req, resp);
            }
            String publicKey = accountService.getPublicKeyIsActive(accountId);
            orderService.sign(orderId, signature, publicKey);
            req.setAttribute("message", "Đã tạo chữ ký thành công");
            resp.sendRedirect("/ATBM/user/order/confirmation/" + orderId);
        } catch (Exception e) {
            req.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
            req.getRequestDispatcher("/views/confirmation.jsp").forward(req, resp);
        }

    }
}
