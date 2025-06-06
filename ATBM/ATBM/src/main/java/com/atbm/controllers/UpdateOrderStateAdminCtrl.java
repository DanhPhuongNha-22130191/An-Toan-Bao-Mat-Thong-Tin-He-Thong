package com.atbm.controllers;

import com.atbm.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/updateState")
public class UpdateOrderStateAdminCtrl extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        orderService = new OrderService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdStr = req.getParameter("orderId");
        String newStatus = req.getParameter("status");

        if (orderIdStr == null || newStatus == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin cập nhật.");
            return;
        }

        try {
            long orderId = Long.parseLong(orderIdStr);
            boolean updated = orderService.updateStatus(orderId, newStatus);

            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/admin/order"); // Đường dẫn trang quản lý đơn hàng
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cập nhật trạng thái thất bại.");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ.");
        }
    }
}
