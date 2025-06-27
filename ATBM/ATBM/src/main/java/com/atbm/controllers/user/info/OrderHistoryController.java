package com.atbm.controllers.user.info;

import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.OrderService;
import com.atbm.utils.HttpUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user/orders")
@RequestScoped
public class OrderHistoryController extends HttpServlet {
    private final OrderService orderService;

    @Inject
    public OrderHistoryController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long accountId = Long.parseLong(session.getAttribute("accountId").toString());
        try {
            // Lấy danh sách đơn hàng
            List<OrderResponse> orders = orderService.getOrdersByAccountId(accountId);
            if (orders == null) {
                orders = new ArrayList<>();
            }

            // Kiểm tra tính toàn vẹn của đơn hàng
            List<Boolean> tamperStatuses = new ArrayList<>();
            for (OrderResponse orderResponse : orders) {
                tamperStatuses.add(!orderResponse.isDigitallySigned()); // true if tampered, false if intact
            }

            // Đặt các thuộc tính vào request
            HttpUtils.setAttribute(req, "orders", orders);
            HttpUtils.setAttribute(req, "tamperStatuses", tamperStatuses);
            HttpUtils.setAttribute(req, "ordersCount", orders.size());

            // Giữ trạng thái tab
            String activeTab = req.getParameter("tab");
            if (activeTab == null || activeTab.isEmpty()) {
                activeTab = "orders";
            }
            HttpUtils.setAttribute(req, "activeTab", activeTab);

            HttpUtils.dispatcher(req, resp, "/views/order-history.jsp");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/order-history.jsp");
        }
    }
}