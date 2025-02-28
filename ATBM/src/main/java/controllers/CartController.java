package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CartDTO;
import services.CartService;

public class CartController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartService service = new CartService();
		long accountId = Long.parseLong(req.getAttribute("accountId").toString());
		CartDTO dto = service.convertToDTO(accountId);
		req.setAttribute("cartDTO", dto);
		req.getRequestDispatcher("").forward(req, resp);
	}

}
