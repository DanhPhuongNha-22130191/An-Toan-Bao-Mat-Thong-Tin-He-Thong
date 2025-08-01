package com.atbm.controllers.cart;

import com.atbm.config.BaseController;
import com.atbm.mapper.FormMapper;
import com.atbm.models.wrapper.request.AddCartRequest;
import com.atbm.models.wrapper.request.UpdateCartRequest;
import com.atbm.models.wrapper.response.CartResponse;
import com.atbm.services.AccountService;
import com.atbm.services.CartService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user/cart")
public class CartController extends BaseController {
    public final static String CART_URL = "/user/cart";
    private final static String VIEW_CART = "/views/cart.jsp";
    private CartService cartService;
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        cartService = CDI.current().select(CartService.class).get();
        accountService = CDI.current().select(AccountService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = getAccountId(req);
        CartResponse response = cartService.getCartByAccountId(accountId);
        String publicKey = accountService.getPublicKeyActive(accountId);
        HttpSession session = req.getSession();
        session.setAttribute("cart", response);
        
        // Kiểm tra xem có public key không
        boolean hasPublicKey = publicKey != null && !publicKey.isEmpty();
        HttpUtils.setAttribute(req, "hasPublicKey", hasPublicKey);
        
        // Nếu không có public key, hiển thị thông báo
        if (!hasPublicKey) {
            HttpUtils.setAttribute(req, "publicKeyWarning", "Bạn cần đăng ký Public Key trước khi thanh toán để đảm bảo tính toàn vẹn của đơn hàng.");
        }
        
        HttpUtils.dispatcher(req, resp, VIEW_CART);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = getAccountId(req);
        try {
            UpdateCartRequest updateCartRequest = FormMapper.bind(req.getParameterMap(), UpdateCartRequest.class);
            cartService.updateQuantity(accountId, updateCartRequest);
            doGet(req, resp);
        } catch (NumberFormatException e) {
            LogUtils.debug(CartController.class, e.getMessage());
            throw new RuntimeException("Dữ liệu thiếu");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = getAccountId(req);
        try {
            UpdateCartRequest updateCartRequest = FormMapper.bind(req.getParameterMap(), UpdateCartRequest.class);
            cartService.removeProductFromCart(accountId, updateCartRequest);
            doGet(req, resp);
        } catch (NumberFormatException e) {
            LogUtils.debug(CartController.class, e.getMessage());
            throw new RuntimeException("Dữ liệu thiếu");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long accountId = getAccountId(req);
        try {
            AddCartRequest addCartRequest = FormMapper.bind(req.getParameterMap(), AddCartRequest.class);
            cartService.addProductToCart(accountId, addCartRequest);
            doGet(req, resp);
        } catch (NumberFormatException e) {
            LogUtils.debug(CartController.class, e.getMessage());
            throw new RuntimeException("Dữ liệu thiếu");
        }
    }
}
