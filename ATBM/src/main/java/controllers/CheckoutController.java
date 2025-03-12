package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CartDTO;
import models.Order;
import models.OrderDetail;
import services.OrderService;

@WebServlet("/user/checkout")
public class CheckoutController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OrderService service = new OrderService();
		String fullName = req.getParameter("full-name");
		String phoneNumber = req.getParameter("phone-number");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		String note = req.getParameter("note");
		OrderDetail orderDetail = new OrderDetail(fullName, phoneNumber, email, address, note);
		CartDTO cartDto = (CartDTO) req.getSession().getAttribute("cartDTO");
		long accountId = (Long) req.getSession().getAttribute("accountId");
		Order order = new Order(accountId, 0, "COD",cartDto,orderDetail);
		if (cartDto.getVoucher() != null) {
			order.setVoucherId(cartDto.getVoucherId());
		}
		service.insert(order);

	}
}
