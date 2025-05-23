package com.atbm.controllers;

import com.atbm.dto.AccountDTO;
import com.atbm.dto.CartDTO;
import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/voucher/*")
public class VoucherController extends HttpServlet {
    private VoucherService voucherService;

    @Override
    public void init() throws ServletException {
        voucherService = new VoucherService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        try {
            if (action.equals("/apply")) {
                applyVoucher(req, resp);
            } else if (action.equals("/remove")) {
                removeVoucher(req, resp);
            } else {
                req.setAttribute("message", "Hành động không hợp lệ");
                req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
        }
    }

    private void applyVoucher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String code = req.getParameter("voucher-code");
            if (code == null || code.trim().isEmpty()) {
                throw new IllegalArgumentException("Vui lòng nhập mã voucher");
            }

            CartDTO cartDTO = (CartDTO) req.getSession().getAttribute("cartDTO");
            if (cartDTO == null || cartDTO.getItems().isEmpty()) {
                throw new IllegalStateException("Giỏ hàng trống, không thể áp dụng voucher");
            }

            var voucher = voucherService.canApplyVoucher(code);
            if (voucher == null) {
                throw new IllegalStateException("Không thể áp dụng voucher");
            }

            cartDTO.setVoucher(voucher);
            req.getSession().setAttribute("cartDTO", cartDTO);
            req.setAttribute("message", "Áp dụng voucher thành công");

        } catch (IllegalArgumentException | IllegalStateException e) {
            req.setAttribute("message", e.getMessage());
        } catch (Exception e) {
            req.setAttribute("message", "Có lỗi xảy ra khi áp dụng voucher");
        }

        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    private void removeVoucher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CartDTO cartDTO = (CartDTO) req.getSession().getAttribute("cartDTO");
            if (cartDTO != null) {
                cartDTO.setVoucher(null);
                req.getSession().setAttribute("cartDTO", cartDTO);
                req.setAttribute("message", "Đã xóa voucher khỏi giỏ hàng");
            }
        } catch (Exception e) {
            req.setAttribute("message", "Có lỗi xảy ra khi xóa voucher");
        }

        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }
}

