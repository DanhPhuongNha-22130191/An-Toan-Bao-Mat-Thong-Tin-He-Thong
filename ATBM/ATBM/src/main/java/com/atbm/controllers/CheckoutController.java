package com.atbm.controllers;


import com.atbm.dto.CartDTO;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/checkout")
public class CheckoutController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/ATBM/views/checkout.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartDTO cartDto = (CartDTO) req.getSession().getAttribute("cartDTO");
		if (cartDto == null) {
			resp.sendRedirect("/user/cart");
		}
		OrderService service = new OrderService();
		String fullName = req.getParameter("full-name");
		String phoneNumber = req.getParameter("phone-number");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		String note = req.getParameter("note");
		OrderDetail orderDetail = new OrderDetail(fullName, phoneNumber, email, address, note);

		dto.AccountDTO accountDTO = (dto.AccountDTO) req.getSession().getAttribute("user");
		long accountId = accountDTO.getAccountId();		Order order = new Order(accountId, 0, "COD", cartDto, orderDetail);
		if (cartDto.getVoucher() != null) {
			order.setVoucherId(cartDto.getVoucherId());
		}
		if (service.insert(order)) {
			cartDto.setOrderDetail(orderDetail);
			cartDto.setOrder(service.getById(service.getIdOrder(accountId)));
			resp.sendRedirect("/ATBM/views/confirmation.jsp");
		}

	}
}
