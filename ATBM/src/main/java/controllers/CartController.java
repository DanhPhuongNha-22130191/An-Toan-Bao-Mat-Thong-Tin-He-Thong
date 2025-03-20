
package controllers;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CartDTO;
import models.CartItem;
import services.CartService;
import services.VoucherService;

@WebServlet("/user/cart")
public class CartController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] productQuantity = req.getParameterValues("cartItem-quantity");
		if (productQuantity != null) {
			CartService service = new CartService();
			for (String string : productQuantity) {
				StringTokenizer tokens = new StringTokenizer(string, "-");
				if (tokens.countTokens() == 2) {
					long cartItemId = Long.parseLong(tokens.nextToken());
					int quantity = Integer.parseInt(tokens.nextToken());
					service.update(new CartItem(cartItemId, quantity));
				}
			}
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartService service = new CartService();
//		long accountId = Long.parseLong(req.getAttribute("accountId").toString());
		long accountId = 101;
		CartDTO dto = service.convertToDTO(accountId);
		Object code = req.getSession().getAttribute("voucher");
		if (code != null) {
			dto.setVoucher(new VoucherService().getByCode(code.toString()));
		}
		req.getSession().setAttribute("cartDTO", dto);
		
		resp.sendRedirect("/ATBM/views/cart.jsp");
	}

}
=======
package controllers;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.CartDTO;
import models.CartItem;
import services.CartService;
import services.VoucherService;

@WebServlet("/user/cart")
public class CartController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] productQuantity = req.getParameterValues("product-quantity");
		if (productQuantity != null) {
			CartService service = new CartService();
//			long accountId = Long.parseLong(req.getAttribute("accountId").toString());
			long accountId = 101;
			for (String string : productQuantity) {
				StringTokenizer tokens = new StringTokenizer(string, "-");
				if (tokens.countTokens() == 2) {
					long productId = Long.parseLong(tokens.nextToken());
					int quantity = Integer.parseInt(tokens.nextToken());
					service.update(new CartItem(accountId, productId, quantity));
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartService service = new CartService();
//		long accountId = Long.parseLong(req.getAttribute("accountId").toString());
		long accountId = 101;
		CartDTO dto = service.convertToDTO(accountId);
		Object code = req.getSession().getAttribute("voucher");
		if (code != null) {
			dto.setVoucher(new VoucherService().getByCode(code.toString()));
		}
		req.setAttribute("cartDTO", dto);
		req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
	}
	

}

