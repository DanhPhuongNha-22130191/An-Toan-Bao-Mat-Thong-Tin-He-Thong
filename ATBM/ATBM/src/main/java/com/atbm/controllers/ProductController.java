package com.atbm.controllers;

import com.atbm.dto.ProductDTO;
import com.atbm.models.Product;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/product")
public class ProductController extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());
	private ProductService productService;

	@Override
	public void init() throws ServletException {
		try {
			LOGGER.info("Khởi tạo ProductService...");
			productService = new ProductService();
			LOGGER.info("ProductController khởi tạo thành công");
		} catch (Exception e) {
			LOGGER.severe("Lỗi khi khởi tạo ProductController: " + e.getMessage());
			throw new ServletException("Không thể khởi tạo ProductController", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		LOGGER.info("Xử lý hành động GET: " + (action != null ? action : "null"));
		try {
			if ("shop".equals(action) || "list".equals(action)) {
				listProducts(req, resp);
			} else if ("details".equals(action)) {
				listProductDetails(req, resp);
			} else {
				LOGGER.warning("Hành động không hợp lệ, mặc định hiển thị danh sách sản phẩm");
				listProducts(req, resp);
			}
		} catch (Exception e) {
			LOGGER.severe("Lỗi khi xử lý hành động GET " + action + ": " + e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu");
		}
	}

	private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			List<ProductDTO> products = productService.getAll().stream()
					.map(this::mapToDTO)
					.collect(Collectors.toList());
			req.setAttribute("products", products);
			LOGGER.info("Lấy danh sách sản phẩm thành công, số lượng: " + products.size());
			req.getRequestDispatcher("/views/shop.jsp").forward(req, resp);
		} catch (Exception e) {
			LOGGER.severe("Lỗi khi hiển thị trang shop: " + e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải trang shop");
		}
	}

	private void listProductDetails(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String idParam = req.getParameter("id");
			if (idParam == null || idParam.isEmpty()) {
				LOGGER.warning("Thiếu tham số id cho chi tiết sản phẩm");
				resp.sendRedirect(req.getContextPath() + "/product?action=shop&error=missingId");
				return;
			}
			Long productId = Long.parseLong(idParam);
			Product product = productService.getById(productId);

			if (product != null) {
				req.setAttribute("product", mapToDTO(product));
				LOGGER.info("Hiển thị chi tiết sản phẩm ID: " + productId);
				req.getRequestDispatcher("/WEB-INF/views/productdetails.jsp").forward(req, resp);
			} else {
				LOGGER.warning("Không tìm thấy sản phẩm với ID: " + productId);
				resp.sendRedirect(req.getContextPath() + "/product?action=shop&error=notfound");
			}
		} catch (NumberFormatException e) {
			LOGGER.warning("ID sản phẩm không hợp lệ: " + req.getParameter("id"));
			resp.sendRedirect(req.getContextPath() + "/product?action=shop&error=invalidId");
		} catch (Exception e) {
			LOGGER.severe("Lỗi khi hiển thị chi tiết sản phẩm: " + e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải chi tiết sản phẩm");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		LOGGER.info("Xử lý hành động POST: " + (action != null ? action : "null"));
		try {
			if ("addToCart".equals(action)) {
				addToCart(req, resp);
			} else {
				LOGGER.warning("Hành động POST không hợp lệ: " + action);
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
			}
		} catch (Exception e) {
			LOGGER.severe("Lỗi khi xử lý hành động POST " + action + ": " + e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu");
		}
	}

	@SuppressWarnings("unchecked")
	private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			HttpSession session = req.getSession();
			Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

			if (cart == null) {
				cart = new HashMap<>();
				session.setAttribute("cart", cart);
			}

			String productIdParam = req.getParameter("productId");
			if (productIdParam == null || productIdParam.isEmpty()) {
				LOGGER.warning("Thiếu tham số productId cho giỏ hàng");
				session.setAttribute("error", "Thiếu ID sản phẩm");
				resp.sendRedirect(req.getContextPath() + "/product?action=shop");
				return;
			}

			Long productId = Long.parseLong(productIdParam);
			cart.put(productId, cart.getOrDefault(productId, 0) + 1);
			LOGGER.info("Thêm sản phẩm vào giỏ hàng: ID = " + productId);

			session.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");

			String referer = req.getHeader("Referer");
			if (referer != null && !referer.contains("error")) {
				resp.sendRedirect(referer);
			} else {
				resp.sendRedirect(req.getContextPath() + "/product?action=shop");
			}
		} catch (NumberFormatException e) {
			LOGGER.warning("ID sản phẩm không hợp lệ: " + req.getParameter("productId"));
			req.getSession().setAttribute("error", "ID sản phẩm không hợp lệ");
			resp.sendRedirect(req.getContextPath() + "/product?action=shop");
		}
	}

	private ProductDTO mapToDTO(Product product) {
		return new ProductDTO(
				product.getProductId(),
				product.getName(),
				product.getPrice(),
				product.getDescription(),
				product.getImage(),
				product.isHaveTrending()
		);
	}
}