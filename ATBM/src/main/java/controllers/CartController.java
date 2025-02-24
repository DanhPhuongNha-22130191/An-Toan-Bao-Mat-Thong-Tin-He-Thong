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
		CartDTO dto = new CartDTO();
		CartService service = new CartService();
		dto.addAll(service.convertToDTO());
		req.setAttribute("cartDTO", dto);
		req.getRequestDispatcher("").forward(req, resp);
	}

}
