package com.atbm.controllers;

import com.atbm.dto.AccountDTO;
import com.atbm.dto.CartDTO;
import com.atbm.services.CartService;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/cart/*")
public class CartController extends HttpServlet {
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        try {
            switch (action) {
                case "/update":
                    handleUpdateCart(req, resp);
                    break;
                case "/add":
                    handleAddToCart(req, resp);
                    break;
                case "/remove":
                    handleRemoveFromCart(req, resp);
                    break;
                case "/clear":
                    handleClearCart(req, resp);
                    break;
                default:
                    req.setAttribute("message", "Hành động không hợp lệ");
                    req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDTO cartDTO = getOrCreateCart(req);
        req.setAttribute("cartDTO", cartDTO);
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    private CartDTO getOrCreateCart(HttpServletRequest req) {
        CartDTO cartDTO = (CartDTO) req.getSession().getAttribute("cartDTO");
        if (cartDTO == null) {
            cartDTO = new CartDTO();
            AccountDTO accountDTO = (AccountDTO) req.getSession().getAttribute("user");
            if (accountDTO != null) {
                cartDTO = cartService.convertToDTO(accountDTO.getAccountId());
            }
            req.getSession().setAttribute("cartDTO", cartDTO);
        }
        return cartDTO;
    }

    private void handleUpdateCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productQuantities = req.getParameterValues("productId-quantity");
        CartDTO cartDTO = getOrCreateCart(req);
        StringBuilder errorMessage = new StringBuilder();

        if (productQuantities != null) {
            for (String item : productQuantities) {
                String[] parts = item.split("-");
                if (parts.length == 2) {
                    try {
                        long productId = Long.parseLong(parts[0]);
                        int quantity = Integer.parseInt(parts[1]);
                        cartService.update(cartDTO.updateQuantity(productId, quantity));
                    } catch (Exception e) {
                        errorMessage.append("Lỗi cập nhật sản phẩm ").append(item).append(": ").append(e.getMessage()).append("<br>");
                    }
                }
            }
        }

        req.setAttribute("cartDTO", cartDTO);
        req.setAttribute("message", !errorMessage.isEmpty() ? errorMessage.toString() : "Cập nhật giỏ hàng thành công");
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }


    private void handleAddToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "";
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            var product = productService.getById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Sản phẩm không tồn tại");
            }

            CartDTO cartDTO = getOrCreateCart(req);
            cartService.insert(cartDTO.add(product, 0, quantity));
            req.setAttribute("cartDTO", cartDTO);
            message = "Thêm vào giỏ hàng thành công";
        } catch (Exception e) {
            message = "Lỗi thêm sản phẩm: " + e.getMessage();
        }

        req.setAttribute("message", message);
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }


    private void handleRemoveFromCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "";
        try {
            long productId = Long.parseLong(req.getParameter("productId"));
            CartDTO cartDTO = getOrCreateCart(req);
            CartDTO.CartItemDTO item = cartDTO.removeItem(productId);
            cartService.delete(item.getCartItemId());
            req.setAttribute("cartDTO", cartDTO);
            message = "Đã xóa sản phẩm khỏi giỏ hàng";
        } catch (Exception e) {
            message = "Lỗi: " + e.getMessage();
        }

        req.setAttribute("message", message);
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }


    private void handleClearCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDTO cartDTO = getOrCreateCart(req);
        cartDTO.clear();
        req.setAttribute("cartDTO", cartDTO);
        req.setAttribute("message", "Đã xóa toàn bộ giỏ hàng");
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

}




