package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.VoucherService;

@WebServlet("/user/voucher")
public class VoucherController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Đặt kiểu phản hồi là text/plain để tránh lỗi hiển thị
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		String code = req.getParameter("voucher-code");
		long accountId = 101;

		VoucherService service = new VoucherService();
		if (service.isVoucherValid(code, accountId)) {
			req.getSession().setAttribute("voucher", code);
			resp.getWriter().write("1");
		} else {
			resp.getWriter().write("0");
		}
	}
}
