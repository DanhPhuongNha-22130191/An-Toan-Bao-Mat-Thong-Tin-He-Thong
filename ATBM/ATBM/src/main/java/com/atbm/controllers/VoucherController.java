
package com.atbm.controllers;


import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/voucher")
public class VoucherController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Đặt kiểu phản hồi là text/plain để tránh lỗi hiển thị
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		String code = req.getParameter("voucher-code");
		long accountId = 101; // Giả lập accountId

		VoucherService service = new VoucherService();
		if (service.applyVoucher(code, accountId)) {
			req.getSession().setAttribute("voucher", code);
			resp.getWriter().write("1"); // Gửi về chuỗi "1"
		} else {
			resp.getWriter().write("0"); // Gửi về chuỗi "0"
		}
	}
}

