package controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CartDTO;
import models.CartItem;
import services.CartService;
@WebServlet("/user/cart")
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
				service.update(new CartItem(accountId, productId, quantity));
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartService service = new CartService();
//		long accountId = Long.parseLong(req.getAttribute("accountId").toString());
		long accountId = 101;
		CartDTO dto = service.convertToDTO(accountId);
		req.setAttribute("cartDTO", dto);
		req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
	}

}
