package com.atbm.controllers;

import com.atbm.dto.AccountDTO;
import com.atbm.dto.CartDTO;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.services.OrderService;
import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/checkout/*")
public class CheckoutController extends HttpServlet {
    private OrderService orderService;
    private VoucherService voucherService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        voucherService = new VoucherService();
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
            req.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }

    private void handleCheckoutPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("user");

        CartDTO cartDTO = (CartDTO) session.getAttribute("cartDTO");
        if (cartDTO == null || cartDTO.getItems().isEmpty()) {
            resp.sendRedirect("/ATBM/user/cart");
            return;
        }

        try {
            // Validate giỏ hàng trước khi hiển thị trang checkout
            cartDTO.validateCart();

            // Kiểm tra voucher nếu có
            if (cartDTO.getVoucher() != null) {
                var voucher = voucherService.getById(cartDTO.getVoucherId());
                if (voucher == null || voucherService.isVoucherValid(voucher.getCode(), accountDTO.getAccountId())) {
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
            req.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }

    private void processCheckout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            AccountDTO accountDTO = (AccountDTO) session.getAttribute("user");

            CartDTO cartDTO = (CartDTO) session.getAttribute("cartDTO");
            if (cartDTO == null || cartDTO.getItems().isEmpty()) {
                throw new IllegalStateException("Giỏ hàng trống");
            }

            // Validate thông tin đơn hàng
            String fullName = req.getParameter("full-name");
            String phoneNumber = req.getParameter("phone-number");
            String email = req.getParameter("email");
            String address = req.getParameter("address");
            String note = req.getParameter("note");
            String paymentMethod = req.getParameter("paymentMethod");

            validateOrderInfo(fullName, phoneNumber, email, address, paymentMethod);

            // Validate giỏ hàng
            cartDTO.validateCart();


            // Tạo đơn hàng
            OrderDetail orderDetail = new OrderDetail(fullName, phoneNumber, email, address, note);
            Order order = new Order(accountDTO.getAccountId(), cartDTO.getShipping(), paymentMethod, cartDTO, orderDetail);

            if (cartDTO.getVoucher() != null) {
                var voucher = voucherService.getById(cartDTO.getVoucherId());
                if (voucherService.isVoucherValid(voucher.getCode(), accountDTO.getAccountId())) {
                    throw new IllegalStateException("Voucher không còn hợp lệ");
                }
                order.setVoucherId(cartDTO.getVoucherId());
            }

            // Xử lý thanh toán
            if ("COD".equals(paymentMethod)) {
                processCODOrder(order, session, req, resp);
                voucherService.insert(cartDTO.getVoucher());
            } else {
                throw new IllegalStateException("Phương thức thanh toán không được hỗ trợ");
            }

        } catch (IllegalStateException e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", "Có lỗi xảy ra khi xử lý đơn hàng");
            req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
        }
    }

    private void processCODOrder(Order order, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (orderService.insert(order)) {
            session.removeAttribute("cartDTO");
            req.setAttribute("message", "Đặt hàng thành công");
            req.getRequestDispatcher("/views/order/confirmation.jsp").forward(req, resp);
        } else {
            throw new IllegalStateException("Không thể tạo đơn hàng, vui lòng thử lại sau");
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
