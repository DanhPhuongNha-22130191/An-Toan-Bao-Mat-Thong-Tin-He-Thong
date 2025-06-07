//package com.atbm.controllers;
//
//import com.atbm.dao.OrderDao;
//import com.atbm.dto.AccountDTO;
//import com.atbm.mail.EmailUtil;
//import com.atbm.models.Account;
//import com.atbm.models.Order;
//import com.atbm.models.OrderDetail;
//import com.atbm.services.AccountService;
//import com.atbm.services.CartService;
//import com.atbm.services.OrderSecurityService;
//import com.atbm.services.OrderService;
//import com.atbm.utils.SignatureUtil;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import jakarta.servlet.http.Part;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.logging.Logger;
//
//@WebServlet("/user/account")
//@MultipartConfig
//public class AccountController extends HttpServlet {
//    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());
//    private AccountService accountService;
//    private OrderService orderService;
//    private OrderSecurityService orderSecurityService;
//    private static final String RECAPTCHA_SECRET_KEY = "6LcooTwrAAAAADsuwNgS3T4IsD9Cu1jmmSbJ5p1Y";
//
//    @Override
//    public void init() throws ServletException {
//        try {
//            LOGGER.info("Khởi tạo AccountService...");
//            accountService = new AccountService();
//            LOGGER.info("Khởi tạo OrderService...");
//            orderService = new OrderService();
//            LOGGER.info("Khởi tạo OrderSecurityService...");
//            orderSecurityService = new OrderSecurityService();
//            LOGGER.info("AccountController khởi tạo thành công");
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi khởi tạo AccountController: " + e.getMessage());
//            throw new ServletException("Không thể khởi tạo AccountController", e);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("action");
//        LOGGER.info("Xử lý hành động POST: " + action);
//        try {
//            switch (action != null ? action : "") {
//                case "register":
//                    register(req, resp);
//                    break;
//                case "login":
//                    login(req, resp);
//                    break;
//                case "updateProfile":
//                    updateProfile(req, resp);
//                    break;
//                case "changePassword":
//                    changePassword(req, resp);
//                    break;
//                case "forgotPassword":
//                    forgotPassword(req, resp);
//                    break;
//                case "revokePublicKey":
//                    revokePublicKey(req, resp);
//                    break;
//                case "uploadPublicKey":
//                    uploadPublicKey(req, resp);
//                    break;
//                default:
//                    req.setAttribute("error", "Hành động không hợp lệ.");
//                    req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            }
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi xử lý hành động POST " + action + ": " + e.getMessage());
//            throw new ServletException("Lỗi xử lý yêu cầu", e);
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("action");
//        LOGGER.info("Xử lý hành động GET: " + action);
//        try {
//            if ("logout".equals(action)) {
//                logout(req, resp);
//            } else if ("profile".equals(action)) {
//                showProfile(req, resp);
//            } else if ("login".equals(action)) {
//                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            } else if ("register".equals(action)) {
//                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
//            } else {
//                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            }
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi xử lý hành động GET " + action + ": " + e.getMessage());
//            throw new ServletException("Lỗi xử lý yêu cầu", e);
//        }
//    }
//
//    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String recaptchaResponse = req.getParameter("g-recaptcha-response");
//        boolean isRecaptchaValid = verifyRecaptcha(recaptchaResponse);
//        if (!isRecaptchaValid) {
//            req.setAttribute("error", "Vui lòng xác nhận bạn không phải là robot.");
//            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
//            return;
//        }
//
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String email = req.getParameter("email");
//        try {
//            LOGGER.info("Đang đăng ký người dùng: " + username);
//            boolean success = accountService.register(username, password, email);
//            if (success) {
//                LOGGER.info("Đăng ký thành công cho người dùng: " + username);
//                resp.sendRedirect(req.getContextPath() + "/views/login.jsp?success=registered");
//            } else {
//                LOGGER.warning("Đăng ký thất bại: Tên tài khoản đã tồn tại - " + username);
//                req.setAttribute("error", "Tên tài khoản đã tồn tại.");
//                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
//            }
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi đăng ký người dùng " + username + ": " + e.getMessage());
//            req.setAttribute("error", "Lỗi khi đăng ký tài khoản.");
//            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
//        }
//    }
//
//    private boolean verifyRecaptcha(String recaptchaResponse) {
//        try {
//            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            String postParams = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + recaptchaResponse;
//            conn.setDoOutput(true);
//            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
//                wr.writeBytes(postParams);
//            }
//
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//                StringBuilder response = new StringBuilder();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                LOGGER.info("Phản hồi Recaptcha: " + response);
//                return response.toString().contains("\"success\": true");
//            }
//        } catch (Exception e) {
//            LOGGER.warning("Xác minh Recaptcha thất bại: " + e.getMessage());
//            return false;
//        }
//    }
//
//    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String recaptchaResponse = req.getParameter("g-recaptcha-response");
//        boolean isRecaptchaValid = verifyRecaptcha(recaptchaResponse);
//        if (!isRecaptchaValid) {
//            req.setAttribute("error", "Vui lòng xác nhận bạn không phải là robot.");
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            return;
//        }
//
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//
//        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
//            req.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            return;
//        }
//
//        try {
//            Account account = accountService.login(username.trim(), password.trim());
//            if (account != null) {
//                AccountDTO accountDTO = new AccountDTO(
//                        account.getAccountId(),
//                        account.getUsername(),
//                        account.getEmail(),
//                        account.getPublicKeyActive()
//                );
//                HttpSession session = req.getSession();
//                session.setAttribute("user", accountDTO);
//                LOGGER.info("Đăng nhập thành công cho người dùng: " + username);
//                resp.sendRedirect(req.getContextPath() + "/product/category");
//            } else {
//                LOGGER.info("Đăng nhập thất bại cho người dùng: " + username);
//                req.setAttribute("error", "Sai tài khoản hoặc mật khẩu.");
//                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            }
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi đăng nhập cho người dùng " + username + ": " + e.getMessage());
//            req.setAttribute("error", "Lỗi khi đăng nhập.");
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//        }
//    }
//
//    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        AccountDTO user = (AccountDTO) session.getAttribute("user");
//        if (user == null) {
//            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
//            return;
//        }
//
//        String newUsername = req.getParameter("username");
//        String newEmail = req.getParameter("email");
//        try {
//            if (newUsername.equals(user.getUsername()) && newEmail.equals(user.getEmail())) {
//                req.setAttribute("message", "Không có thay đổi nào để cập nhật.");
//            } else {
//                user.setUsername(newUsername);
//                user.setEmail(newEmail);
//                boolean updated = accountService.updateProfile(user);
//                if (updated) {
//                    session.setAttribute("user", user);
//                    req.setAttribute("message", "Cập nhật thông tin thành công!");
//                } else {
//                    req.setAttribute("error", "Lỗi cập nhật thông tin.");
//                }
//            }
//            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi cập nhật hồ sơ cho người dùng " + user.getUsername() + ": " + e.getMessage());
//            req.setAttribute("error", "Lỗi khi cập nhật hồ sơ.");
//            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
//        }
//    }
//
//    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        AccountDTO user = (AccountDTO) session.getAttribute("user");
//        if (user == null) {
//            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
//            return;
//        }
//
//        String oldPassword = req.getParameter("oldPassword");
//        String newPassword = req.getParameter("newPassword");
//        try {
//            if (!accountService.checkPassword(user.getAccountId(), oldPassword)) {
//                req.setAttribute("error", "Mật khẩu cũ không đúng!");
//            } else if (oldPassword.equals(newPassword)) {
//                req.setAttribute("error", "Mật khẩu mới không được giống mật khẩu cũ.");
//            } else {
//                boolean updated = accountService.updatePassword(user.getAccountId(), newPassword);
//                if (updated) {
//                    req.setAttribute("message", "Mật khẩu đã được cập nhật thành công.");
//                } else {
//                    req.setAttribute("error", "Lỗi khi cập nhật mật khẩu.");
//                }
//            }
//            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi đổi mật khẩu cho người dùng " + user.getUsername() + ": " + e.getMessage());
//            req.setAttribute("error", "Lỗi khi đổi mật khẩu.");
//            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
//        }
//    }
//
//    private void forgotPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String recaptchaResponse = req.getParameter("g-recaptcha-response");
//        boolean isRecaptchaValid = verifyRecaptcha(recaptchaResponse);
//        if (!isRecaptchaValid) {
//            req.setAttribute("error", "Vui lòng xác nhận bạn không phải là robot.");
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//            return;
//        }
//
//        String email = req.getParameter("email");
//        try {
//            Account account = accountService.getAccountByEmail(email);
//            if (account != null) {
//                String newPassword = generateRandomPassword();
//                LOGGER.info("Mật khẩu mới được tạo cho email " + email + ": " + newPassword);
//                boolean updated = accountService.updatePassword(account.getAccountId(), newPassword);
//                if (updated) {
//                    String emailContent = "Chào bạn,\n\n" +
//                            "Mật khẩu mới của bạn là: " + newPassword + "\n" +
//                            "Vui lòng sử dụng mật khẩu này để đăng nhập và đổi mật khẩu sớm nhất có thể.\n\n" +
//                            "Trân trọng,\nWatchShop";
//                    boolean emailSent = EmailUtil.sendEmail(email, "Khôi phục mật khẩu", emailContent);
//                    if (emailSent) {
//                        LOGGER.info("Email khôi phục mật khẩu đã gửi thành công đến: " + email);
//                        req.setAttribute("message", "Mật khẩu mới đã được gửi đến email của bạn.");
//                    } else {
//                        LOGGER.warning("Gửi email khôi phục mật khẩu thất bại cho: " + email);
//                        req.setAttribute("error", "Gửi email thất bại. Vui lòng thử lại sau.");
//                    }
//                } else {
//                    LOGGER.warning("Không thể cập nhật mật khẩu cho email: " + email);
//                    req.setAttribute("error", "Không thể cập nhật mật khẩu. Vui lòng thử lại.");
//                }
//            } else {
//                LOGGER.info("Email không tồn tại trong hệ thống: " + email);
//                req.setAttribute("error", "Email không tồn tại trong hệ thống.");
//            }
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi khôi phục mật khẩu cho email " + email + ": " + e.getMessage());
//            req.setAttribute("error", "Lỗi khi khôi phục mật khẩu.");
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//        }
//    }
//
//    private String generateRandomPassword() {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder password = new StringBuilder();
//        Random random = new Random();
//        for (int i = 0; i < 8; i++) {
//            password.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return password.toString();
//    }
//
//    private void uploadPublicKey(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        AccountDTO user = (AccountDTO) session.getAttribute("user");
//        if (user == null) {
//            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
//            return;
//        }
//
//        String publicKeyText = req.getParameter("publicKeyText");
//        Part filePart = req.getPart("publicKeyFile");
//        String publicKey = null;
//
//        if (filePart != null && filePart.getSize() > 0) {
//            publicKey = extractPublicKeyFromFile(filePart);
//        } else if (publicKeyText != null && !publicKeyText.trim().isEmpty()) {
//            publicKey = publicKeyText.trim();
//        }
//
//        if (publicKey != null) {
//            try {
//                user.setPublicKeyActive(publicKey);
//                boolean updated = accountService.updateProfile(user);
//                if (updated) {
//                    session.setAttribute("user", user);
//                    req.setAttribute("message", "Public Key đã được cập nhật thành công.");
//                } else {
//                    req.setAttribute("error", "Lỗi khi cập nhật Public Key.");
//                }
//            } catch (Exception e) {
//                LOGGER.severe("Lỗi khi cập nhật public key: " + e.getMessage());
//                req.setAttribute("error", "Lỗi khi cập nhật Public Key.");
//            }
//        } else {
//            req.setAttribute("error", "Vui lòng tải lên file hoặc nhập Public Key.");
//        }
//        showProfile(req, resp);
//    }
//
//    private String extractPublicKeyFromFile(Part filePart) {
//        try {
//            String fileContent = new String(filePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
//            String[] lines = fileContent.split("\n");
//            StringBuilder keyBuilder = new StringBuilder();
//            for (String line : lines) {
//                if (!line.startsWith("-----")) {
//                    keyBuilder.append(line.trim());
//                }
//            }
//            return keyBuilder.toString();
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi trích xuất public key từ file: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private void revokePublicKey(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        AccountDTO user = (AccountDTO) session.getAttribute("user");
//        if (user == null) {
//            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
//            return;
//        }
//
//        try {
//            accountService.revokePublicKey(user.getAccountId());
//            user.setPublicKeyActive(null);
//            session.setAttribute("user", user);
//            req.setAttribute("message", "Public Key đã được thu hồi.");
//            showProfile(req, resp);
//        } catch (Exception e) {
//            LOGGER.severe("Lỗi khi thu hồi public key cho người dùng: " + user.getUsername() + ": " + e.getMessage());
//            req.setAttribute("error", "Lỗi khi thu hồi Public Key.");
//            showProfile(req, resp);
//        }
//    }
//
//    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        HttpSession session = req.getSession();
//        session.invalidate();
//        resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
//    }
//
//    private void showProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        LOGGER.info("=== SHOWPROFILE DEBUG START ===");
//        LOGGER.info("Session ID: " + session.getId());
//
//        AccountDTO user = (AccountDTO) session.getAttribute("user");
//        if (user == null) {
//            LOGGER.severe("User not found in session. Redirecting to login.");
//            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
//            return;
//        }
//
//        LOGGER.info("Account ID from session: " + user.getAccountId());
//        LOGGER.info("Username from session: " + user.getUsername());
//
//        try {
//            if (orderService == null) {
//                LOGGER.severe("OrderService is null. Initializing new instance.");
//                orderService = new OrderService();
//            }
//
//            LOGGER.info("Calling orderService.getOrdersByAccountId with Account ID: " + user.getAccountId());
//            List<Order> orders = orderService.getOrdersByAccountId(user.getAccountId());
//
//            if (orders == null) {
//                LOGGER.warning("getOrdersByAccountId returned null for Account ID: " + user.getAccountId());
//                orders = new ArrayList<>();
//            }
//
//            LOGGER.info("Retrieved " + orders.size() + " orders for Account ID: " + user.getAccountId());
//
//            CartService cartService = new CartService();
//            OrderDao orderDao = new OrderDao();
//            for (Order order : orders) {
//                LOGGER.info("Processing Order ID: " + order.getOrderId());
//                try {
//                    if (order.getCartDTO() == null) {
//                        order.setCartDTO(cartService.convertToDTO(order.getOrderId()));
//                        LOGGER.info("Set CartDTO for Order ID: " + order.getOrderId());
//                    }
//                } catch (Exception e) {
//                    LOGGER.warning("Failed to set CartDTO for Order ID: " + order.getOrderId() + ": " + e.getMessage());
//                }
//                try {
//                    if (order.getOrderDetail() == null) {
//                        order.setOrderDetail(orderDao.getOrderDetailByOrderId(order.getOrderId()));
//                        LOGGER.info("Set OrderDetail for Order ID: " + order.getOrderId() +
//                                ", FullName: " + (order.getOrderDetail() != null ? order.getOrderDetail().getFullName() : "Null"));
//                    }
//                } catch (Exception e) {
//                    LOGGER.warning("Failed to set OrderDetail for Order ID: " + order.getOrderId() + ": " + e.getMessage());
//                }
//            }
//
//            for (int i = 0; i < orders.size(); i++) {
//                Order order = orders.get(i);
//                LOGGER.info("Order " + (i + 1) + ":");
//                LOGGER.info("  - Order ID: " + order.getOrderId());
//                LOGGER.info("  - Account ID: " + order.getAccountId());
//                LOGGER.info("  - Status: " + order.getStatus());
//                LOGGER.info("  - Payment Method: " + order.getPaymentMethod());
//                LOGGER.info("  - Order Date: " + order.getOrderDate());
//                LOGGER.info("  - Total Amount: " + order.getTotalAmount());
//                LOGGER.info("  - Shipping: " + order.getShipping());
//                LOGGER.info("  - Voucher ID: " + (order.getVoucherId() != null ? order.getVoucherId() : "NULL"));
//                LOGGER.info("  - CartDTO: " + (order.getCartDTO() != null ? "Present" : "Null"));
//                LOGGER.info("  - OrderDetail: " + (order.getOrderDetail() != null ?
//                        "Present (Name: " + order.getOrderDetail().getFullName() + ")" : "Null"));
//            }
//
//            req.setAttribute("orders", orders);
//            req.setAttribute("ordersCount", orders.size());
//            req.setAttribute("debugInfo", "Total orders: " + orders.size() + " for Account ID: " + user.getAccountId());
//
//            // Xử lý tab active
//            String activeTab = req.getParameter("tab");
//            if (activeTab == null || activeTab.isEmpty()) {
//                activeTab = "profile"; // Mặc định là profile
//            }
//            req.setAttribute("activeTab", activeTab);
//            LOGGER.info("Active tab: " + activeTab);
//
//            LOGGER.info("Set request attributes: orders=" + orders.size() + ", ordersCount=" + orders.size() + ", debugInfo=" + req.getAttribute("debugInfo"));
//            LOGGER.info("Forwarding to profile.jsp");
//            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
//
//        } catch (Exception e) {
//            LOGGER.severe("Error in showProfile for Account ID " + user.getAccountId() + ": " + e.getMessage());
//            e.printStackTrace();
//
//            req.setAttribute("orders", new ArrayList<>());
//            req.setAttribute("ordersCount", 0);
//            req.setAttribute("error", "Error loading orders: " + e.getMessage());
//            req.setAttribute("debugInfo", "Error occurred: " + e.getMessage());
//            req.setAttribute("activeTab", "profile");
//            LOGGER.info("Forwarding to profile.jsp with error: " + e.getMessage());
//
//            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
//        } finally {
//            LOGGER.info("=== SHOWPROFILE DEBUG END ===");
//        }
//    }
//
//    private String generateOrderHash(Order order) {
//        try {
//            OrderDetail detail = order.getOrderDetail();
//            if (detail == null) {
//                LOGGER.warning("Order detail is null for Order ID: " + order.getOrderId());
//                return "Null order detail";
//            }
//            StringBuilder data = new StringBuilder();
//            data.append(order.getOrderId())
//                    .append(order.getOrderDate().toString())
//                    .append(order.getPaymentMethod())
//                    .append(detail.getFullName())
//                    .append(detail.getPhone())
//                    .append(detail.getEmail())
//                    .append(detail.getAddress())
//                    .append(order.getCartDTO().getItems().toString())
//                    .append(order.getVoucherId() != null ? order.getVoucherId() : "none");
//            return SignatureUtil.hash(data.toString());
//        } catch (Exception e) {
//            LOGGER.warning("Error generating hash for Order ID: " + order.getOrderId() + ": " + e.getMessage());
//            return "Hash error";
//        }
//    }
//}