package controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CartDTO;
import models.CartItem;
import services.CartService;

public class CartController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("update".equals(action.toLowerCase())) {
			updateCart(req, resp);
		}
	}

	private void updateCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] productQuantity = req.getParameterValues("product-quantity");
		if (productQuantity != null) {
			CartService service = new CartService();
			long accountId = Long.parseLong(req.getAttribute("accountId").toString());
			for (String string : productQuantity) {
				StringTokenizer tokens = new StringTokenizer(string, "-");
				long productId = Long.parseLong(tokens.nextToken());
				int quantity = Integer.parseInt(tokens.nextToken());
				service.updateCart(new CartItem(accountId, productId, quantity));
			}
		}

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
