
package com.atbm.controllers;


import com.atbm.dto.CartDTO;
import com.atbm.services.CartService;
import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.StringTokenizer;

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
					service.update(new models.CartItem(cartItemId, quantity));
				}
			}
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CartService service = new CartService();
		dto.AccountDTO accountDTO = (dto.AccountDTO) req.getSession().getAttribute("user");
		long accountId = accountDTO.getAccountId();
		CartDTO dto = service.convertToDTO(accountId);
		Object code = req.getSession().getAttribute("voucher");
		if (code != null) {
			dto.setVoucher(new VoucherService().getByCode(code.toString()));
		}
		req.getSession().setAttribute("cartDTO", dto);
		
		resp.sendRedirect("/ATBM/views/cart.jsp");
	}

}




