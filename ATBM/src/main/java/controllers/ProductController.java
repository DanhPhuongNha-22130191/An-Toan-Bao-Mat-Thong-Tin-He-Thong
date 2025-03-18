package controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import dto.ProductDTO;
import models.Product;
import services.ProductService;

public class ProductController extends HttpServlet {
	private ProductService productService = new ProductService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("list".equals(action) || "shop".equals(action)) {
			listProducts(req, resp);
		} else if ("details".equals(action)) {
			listProductDetails(req, resp);
		} else {
			listProducts(req, resp);
		}
	}

	private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ProductDTO> products = productService.getAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		HttpSession session = req.getSession();
		session.setAttribute("products", products);

	    req.getRequestDispatcher("shop.jsp").forward(req, resp);
	}

	private void listProductDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long productId = Long.parseLong(req.getParameter("id"));
		Product product = productService.getById(productId);

		if (product != null) {
			req.setAttribute("product", mapToDTO(product));
			req.getRequestDispatcher("productdetails.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("shop.jsp?error=notfound");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("addToCart".equals(action)) {
			addToCart(req, resp);
		}
	}

	@SuppressWarnings("unchecked")
	private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

		if (cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}

		Long productId = Long.parseLong(req.getParameter("productId"));
		cart.put(productId, cart.getOrDefault(productId, 0) + 1);

		// Thêm thông báo vào session
		session.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");

		// Chuyển hướng trở lại trang trước đó hoặc shop.jsp
		String referer = req.getHeader("Referer");
		if (referer != null) {
			resp.sendRedirect(referer);
		} else {
			resp.sendRedirect("ProductController?action=shop");
		}
	}

	private ProductDTO mapToDTO(Product product) {
		return new ProductDTO(product.getProductId(), product.getCategoryId(), product.getName(),
				product.getPrice(), product.getDescription(), product.getImage(), product.isHaveTrending());
	}
}
