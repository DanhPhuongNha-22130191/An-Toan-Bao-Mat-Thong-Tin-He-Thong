package com.atbm.controllers.user.info;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.OrderService;
import com.atbm.utils.HttpUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user/history-orders")
public class OrderHistoryController extends BaseController {
    @Inject
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long accountId = getAccountId(req);
            List<OrderResponse> orders = orderService.getOrdersByAccountId(accountId);
            if (orders == null) {
                orders = new ArrayList<>();
            }

            List<Boolean> tamperStatuses = new ArrayList<>();
            for (OrderResponse orderResponse : orders) {
                tamperStatuses.add(!orderResponse.isDigitallySigned());
            }

            HttpUtils.setAttribute(req, "orders", orders);
            HttpUtils.setAttribute(req, "tamperStatuses", tamperStatuses);
            HttpUtils.setAttribute(req, "ordersCount", orders.size());
            HttpUtils.setAttribute(req, "activeTab", "order-history");
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (RuntimeException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }
}