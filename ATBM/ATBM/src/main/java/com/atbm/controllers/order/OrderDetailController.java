package com.atbm.controllers.order;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.OrderService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.UrlUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/user/order/detail/*")
public class OrderDetailController extends BaseController {
    public final static String ORDER_DETAIL_URL = "/user/order/detail/";
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = CDI.current().select(OrderService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = getAccountId(req);
        long orderId = getOrderId(req);
        OrderResponse order = orderService.getOrderById(orderId, id);
        HttpUtils.setAttribute(req, "order", order);
        HttpUtils.dispatcher(req, resp, "/views/orderDetail.jsp");
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = getAccountId(req);
            long orderId = getOrderId(req);
            String signature = req.getParameter("signature");
            
            if (signature == null || signature.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Chữ ký không được bỏ trống");
                return;
            }
            
            orderService.updateSignature(id, orderId, signature);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Ký đơn hàng thành công");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Có lỗi xảy ra khi ký đơn hàng");
        }
    }

    private long getOrderId(HttpServletRequest req) {
        try {
            return Long.parseLong(Objects.requireNonNull(UrlUtils.getLastPathSegment(req)));
        } catch (NumberFormatException | NullPointerException e) {
            throw new RuntimeException("Đường dẫn không hợp lệ");
        }
    }
}
