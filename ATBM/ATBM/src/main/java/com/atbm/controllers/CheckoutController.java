package com.atbm.controllers;

import com.atbm.dto.AccountDTO;
import com.atbm.dto.CartDTO;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.models.OrderSecurity;
import com.atbm.services.AccountService;
import com.atbm.services.OrderService;
import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

@WebServlet("/user/checkout/*")
public class CheckoutController extends HttpServlet {
    private OrderService orderService;
    private VoucherService voucherService;
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        voucherService = new VoucherService();
        accountService = new AccountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        try {
            if (action == null || action.equals("/")) {
                handleCheckoutPage(req, resp);
            } else {
                req.setAttribute("message", "Hành động không hợp lệ");
                req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Lỗi hệ thống: " + e.toString());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }

    private void handleCheckoutPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("user");
        CartDTO cartDTO = (CartDTO) session.getAttribute("cartDTO");

        if (cartDTO == null || cartDTO.getItems() == null || cartDTO.getItems().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/user/cart");
            return;
        }

        try {
            cartDTO.validateCart();

            // Kiểm tra voucher
            if (cartDTO.getVoucher() != null) {
                var voucher = voucherService.getById(cartDTO.getVoucherId());
                if (voucher == null || !voucherService.isVoucherValid(voucher.getCode(), accountDTO.getAccountId())) {
                    cartDTO.setVoucher(null);
                    session.setAttribute("cartDTO", cartDTO);
                    req.setAttribute("message", "Voucher không còn hợp lệ");
                }
            }

            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        try {
            if (action == null || action.equals("/")) {
                processCheckout(req, resp);
            } else {
                req.setAttribute("message", "Hành động không hợp lệ");
                req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Có lỗi xảy ra khi xử lý đơn hàng: " + e.toString());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }

    private void processCheckout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("user");
        CartDTO cartDTO = (CartDTO) session.getAttribute("cartDTO");

        if (cartDTO == null || cartDTO.getItems() == null || cartDTO.getItems().isEmpty()) {
            req.setAttribute("message", "Giỏ hàng trống!");
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
            return;
        }

        String fullName = req.getParameter("full-name");
        String phoneNumber = req.getParameter("phone-number");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String note = req.getParameter("note");
        String paymentMethod = req.getParameter("paymentMethod");
        String signature = req.getParameter("signature");

        try {
            validateOrderInfo(fullName, phoneNumber, email, address, paymentMethod);

            cartDTO.validateCart();

            OrderDetail orderDetail = new OrderDetail(fullName, phoneNumber, email, address, note);
            OrderSecurity orderSecurity = new OrderSecurity(signature, accountService.getPublicKeyIsActive(accountDTO.getAccountId()));
            Order order = new Order(accountDTO.getAccountId(), cartDTO.getShipping(), paymentMethod, cartDTO, orderDetail);
            order.setOrderSecurity(orderSecurity);

            if (cartDTO.getVoucher() != null) {
                var voucher = voucherService.getById(cartDTO.getVoucherId());
                if (!voucherService.isVoucherValid(voucher.getCode(), accountDTO.getAccountId())) {
                    throw new IllegalStateException("Voucher không còn hợp lệ");
                }
                order.setVoucherId(cartDTO.getVoucherId());
            }

            if ("COD".equals(paymentMethod)) {
                processCODOrder(order, session, req, resp);
            } else {
                throw new IllegalStateException("Phương thức thanh toán không được hỗ trợ");
            }
        } catch (IllegalStateException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", "Có lỗi xảy ra khi xử lý đơn hàng: " + e.toString());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }

    private void processCODOrder(Order order, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        order.setStatus("Đang giao hàng");
        order.setOrderDate(new Date(Instant.ofEpochMilli(System.currentTimeMillis()).toEpochMilli()));
        Long orderId = orderService.createOrder(order);

        if (orderId != null) {
            session.removeAttribute("cartDTO");
            resp.sendRedirect(req.getContextPath() + "/user/order/confirmation?orderId=" + orderId);
        } else {
            req.setAttribute("message", "Không thể tạo đơn hàng. Vui lòng thử lại!");
            try {
                req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
            } catch (IllegalStateException ex) {
            }
        }
    }

    private void validateOrderInfo(String fullName, String phoneNumber, String email,
                                   String address, String paymentMethod) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalStateException("Vui lòng nhập họ tên");
        }
        if (phoneNumber == null || !phoneNumber.matches("^\\d{10}$")) {
            throw new IllegalStateException("Số điện thoại không hợp lệ");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalStateException("Email không hợp lệ");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalStateException("Vui lòng nhập địa chỉ");
        }
        if (paymentMethod == null || !paymentMethod.matches("^(COD|VNPAY|MOMO)$")) {
            throw new IllegalStateException("Phương thức thanh toán không hợp lệ");
        }
    }
}