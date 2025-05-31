package com.atbm.controllers;

import com.atbm.dto.AccountDTO;
import com.atbm.mail.EmailUtil;
import com.atbm.models.Account;
import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.services.AccountService;
import com.atbm.services.OrderSecurityService;
import com.atbm.services.OrderService;
import com.atbm.utils.SignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@WebServlet("/user/account")
public class AccountController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private AccountService accountService;
    private OrderService orderService;
    private OrderSecurityService orderSecurityService;
    private static final String RECAPTCHA_SECRET_KEY = "6LcooTwrAAAAADsuwNgS3T4IsD9Cu1jmmSbJ5p1Y";

    @Override
    public void init() throws ServletException {
        try {
            LOGGER.info("Khởi tạo AccountService...");
            accountService = new AccountService();
            LOGGER.info("Khởi tạo OrderService...");
            orderService = new OrderService();
            LOGGER.info("Khởi tạo OrderSecurityService...");
            orderSecurityService = new OrderSecurityService();
            LOGGER.info("AccountController khởi tạo thành công");
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi khởi tạo AccountController: " + e.getMessage());
            throw new ServletException("Không thể khởi tạo AccountController", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        LOGGER.info("Xử lý hành động POST: " + action);
        try {
            switch (action != null ? action : "") {
                case "register":
                    register(req, resp);
                    break;
                case "login":
                    login(req, resp);
                    break;
                case "updateProfile":
                    updateProfile(req, resp);
                    break;
                case "changePassword":
                    changePassword(req, resp);
                    break;
                case "forgotPassword":
                    forgotPassword(req, resp);
                    break;
                case "generatePublicKey":
                    generatePublicKey(req, resp);
                    break;
                case "revokePublicKey":
                    revokePublicKey(req, resp);
                    break;
                default:
                    req.setAttribute("error", "Hành động không hợp lệ.");
                    req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi xử lý hành động POST " + action + ": " + e.getMessage());
            throw new ServletException("Lỗi xử lý yêu cầu", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        LOGGER.info("Xử lý hành động GET: " + action);
        try {
            if ("logout".equals(action)) {
                logout(req, resp);
            } else if ("profile".equals(action)) {
                showProfile(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi xử lý hành động GET " + action + ": " + e.getMessage());
            throw new ServletException("Lỗi xử lý yêu cầu", e);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String recaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean isRecaptchaValid = verifyRecaptcha(recaptchaResponse);
        if (!isRecaptchaValid) {
            req.setAttribute("error", "Vui lòng xác nhận bạn không phải là robot.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        try {
            LOGGER.info("Đang đăng ký người dùng: " + username);
            boolean success = accountService.register(username, password, email);
            if (success) {
                LOGGER.info("Đăng ký thành công cho người dùng: " + username);
                resp.sendRedirect(req.getContextPath() + "/views/login.jsp?success=registered");
            } else {
                LOGGER.warning("Đăng ký thất bại: Tên tài khoản đã tồn tại - " + username);
                req.setAttribute("error", "Tên tài khoản đã tồn tại.");
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi đăng ký người dùng " + username + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi đăng ký tài khoản.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }

    private boolean verifyRecaptcha(String recaptchaResponse) {
        try {
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            String postParams = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + recaptchaResponse;
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.writeBytes(postParams);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                LOGGER.info("Phản hồi Recaptcha: " + response);
                return response.toString().contains("\"success\": true");
            }
        } catch (Exception e) {
            LOGGER.warning("Xác minh Recaptcha thất bại: " + e.getMessage());
            return false;
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        try {
            Account account = accountService.login(username.trim(), password.trim());
            if (account != null) {
                AccountDTO accountDTO = new AccountDTO(
                        account.getAccountId(),
                        account.getUsername(),
                        account.getEmail(),
                        account.getPublicKeyActive()
                );
                HttpSession session = req.getSession();
                session.setAttribute("user", accountDTO);
                LOGGER.info("Đăng nhập thành công cho người dùng: " + username);
                resp.sendRedirect(req.getContextPath() + "/product?action=shop");
            } else {
                LOGGER.info("Đăng nhập thất bại cho người dùng: " + username);
                req.setAttribute("error", "Sai tài khoản hoặc mật khẩu.");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi đăng nhập cho người dùng " + username + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi đăng nhập.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        String newUsername = req.getParameter("username");
        String newEmail = req.getParameter("email");
        try {
            if (newUsername.equals(user.getUsername()) && newEmail.equals(user.getEmail())) {
                req.setAttribute("message", "Không có thay đổi nào để cập nhật.");
            } else {
                user.setUsername(newUsername);
                user.setEmail(newEmail);
                boolean updated = accountService.updateProfile(user);
                if (updated) {
                    session.setAttribute("user", user);
                    req.setAttribute("message", "Cập nhật thông tin thành công!");
                } else {
                    req.setAttribute("error", "Lỗi cập nhật thông tin.");
                }
            }
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi cập nhật hồ sơ cho người dùng " + user.getUsername() + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi cập nhật hồ sơ.");
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        }
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        try {
            if (!accountService.checkPassword(user.getAccountId(), oldPassword)) {
                req.setAttribute("error", "Mật khẩu cũ không đúng!");
            } else if (oldPassword.equals(newPassword)) {
                req.setAttribute("error", "Mật khẩu mới không được giống mật khẩu cũ.");
            } else {
                boolean updated = accountService.updatePassword(user.getAccountId(), newPassword);
                if (updated) {
                    req.setAttribute("message", "Mật khẩu đã được cập nhật thành công.");
                } else {
                    req.setAttribute("error", "Lỗi khi cập nhật mật khẩu.");
                }
            }
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi đổi mật khẩu cho người dùng " + user.getUsername() + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi đổi mật khẩu.");
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        }
    }

    private void forgotPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        try {
            Account account = accountService.getAccountByEmail(email);
            if (account != null) {
                String newPassword = generateRandomPassword();
                LOGGER.info("Mật khẩu mới được tạo cho email " + email + ": " + newPassword);
                boolean updated = accountService.updatePassword(account.getAccountId(), newPassword);
                if (updated) {
                    String emailContent = "Chào bạn,\n\n" +
                            "Mật khẩu mới của bạn là: " + newPassword + "\n" +
                            "Vui lòng sử dụng mật khẩu này để đăng nhập và đổi mật khẩu sớm nhất có thể.\n\n" +
                            "Trân trọng,\nWatchShop";
                    boolean emailSent = EmailUtil.sendEmail(email, "Khôi phục mật khẩu", emailContent);
                    if (emailSent) {
                        LOGGER.info("Email khôi phục mật khẩu đã gửi thành công đến: " + email);
                        req.setAttribute("message", "Mật khẩu mới đã được gửi đến email của bạn.");
                    } else {
                        LOGGER.warning("Gửi email khôi phục mật khẩu thất bại cho: " + email);
                        req.setAttribute("error", "Gửi email thất bại. Vui lòng thử lại sau.");
                    }
                } else {
                    LOGGER.warning("Không thể cập nhật mật khẩu cho email: " + email);
                    req.setAttribute("error", "Không thể cập nhật mật khẩu. Vui lòng thử lại.");
                }
            } else {
                LOGGER.info("Email không tồn tại trong hệ thống: " + email);
                req.setAttribute("error", "Email không tồn tại trong hệ thống.");
            }
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi khôi phục mật khẩu cho email " + email + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi khôi phục mật khẩu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    private void generatePublicKey(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        try {
            String publicKey = accountService.generatePublicKey(user.getAccountId());
            if (publicKey != null) {
                user.setPublicKeyActive(publicKey);
                session.setAttribute("user", user);
                req.setAttribute("message", "Public Key mới đã được tạo thành công.");
            } else {
                req.setAttribute("error", "Lỗi khi tạo Public Key.");
            }
            showProfile(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi tạo public key cho người dùng " + user.getUsername() + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi tạo Public Key.");
            showProfile(req, resp);
        }
    }

    private void revokePublicKey(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        try {
            accountService.revokePublicKey(user.getAccountId());
            user.setPublicKeyActive(null);
            session.setAttribute("user", user);
            req.setAttribute("message", "Public Key đã được thu hồi.");
            showProfile(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi thu hồi public key cho người dùng " + user.getUsername() + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi thu hồi Public Key.");
            showProfile(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
    }

    private void showProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        try {
            List<Order> orders = orderService.getOrdersByAccountId(user.getAccountId());
            for (Order order : orders) {
                boolean isTampered = orderSecurityService.isOrderTampered(order);
                String orderHash = generateOrderHash(order);
                req.setAttribute("order_" + order.getOrderId() + "_isTampered", isTampered);
                req.setAttribute("order_" + order.getOrderId() + "_hash", orderHash);
            }
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi hiển thị hồ sơ cho người dùng " + user.getUsername() + ": " + e.getMessage());
            req.setAttribute("error", "Lỗi khi tải trang hồ sơ.");
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
        }
    }

    private String generateOrderHash(Order order) {
        try {
            OrderDetail detail = order.getOrderDetail();
            if (detail == null) {
                LOGGER.warning("Chi tiết đơn hàng null cho đơn hàng ID: " + order.getOrderId());
                return "Lỗi tạo hash";
            }
            StringBuilder data = new StringBuilder();
            data.append(order.getOrderId())
                    .append(order.getOrderDate().toString())
                    .append(order.getPaymentMethod())
                    .append(detail.getFullName())
                    .append(detail.getPhone())
                    .append(detail.getEmail())
                    .append(detail.getAddress())
                    .append(order.getCartDTO().getItems().toString())
                    .append(order.getVoucherId() != null ? order.getVoucherId() : "none");
            return SignatureUtil.hash(data.toString());
        } catch (Exception e) {
            LOGGER.warning("Lỗi khi tạo hash đơn hàng cho ID: " + order.getOrderId() + ": " + e.getMessage());
            return "Lỗi tạo hash";
        }
    }
}