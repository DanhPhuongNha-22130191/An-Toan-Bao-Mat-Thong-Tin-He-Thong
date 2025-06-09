package com.atbm.controllers;

import com.atbm.dto.AccountDTO;
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

@WebServlet("/user/order/confirmation")
public class ConfirmationController extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;
    private VoucherService voucherService;
    private AccountService accountService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String message = "";

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
            req.setAttribute("message", message);
            try {
                orderId = Long.parseLong(req.getParameter("orderId"));
                Order order = orderService.getById(orderId);
                if (order == null) {
                    req.setAttribute("message", "Đơn hàng không tồn tại!");
                    req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
                    return;
                }
                OrderSecurity orderSecurity = orderService.getSecurity(orderId);
                order.setCartDTO(cartService.convertToDTO(orderId));
                if (order.getCartDTO() != null && order.getVoucherId() != null)
                    order.getCartDTO().setVoucher(voucherService.getById(order.getVoucherId()));
                req.setAttribute("order", order);
                req.setAttribute("orderSecurity", orderSecurity);
                req.setAttribute("orderDetail", order.getOrderDetail());
                req.setAttribute("voucher", (order.getCartDTO() != null) ? order.getCartDTO().getVoucher() : null);
                req.setAttribute("cartDTO", order.getCartDTO());
                req.setAttribute("dateFormatter", DATE_FORMATTER);
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                req.setAttribute("message", "Không tìm thấy đơn hàng!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
        }
        req.getRequestDispatcher("/views/confirmation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long orderId = req.getParameter("orderId") == null ? null : Long.parseLong(req.getParameter("orderId"));
        try {
            AccountDTO acc = req.getSession().getAttribute("user") == null ? null : (AccountDTO) req.getSession().getAttribute("user");
            String signature = req.getParameter("signature");
            String publicKey = accountService.getPublicKeyIsActive(acc.getAccountId());
            orderService.sign(orderId, signature, publicKey);
            message = "Đã ký thành công";
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/user/order/confirmation?orderId=" + orderId);
    }
}