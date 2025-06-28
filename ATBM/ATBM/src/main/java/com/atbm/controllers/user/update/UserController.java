package com.atbm.controllers.user.update;

import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.AccountService;
import com.atbm.services.OrderService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user/update")
@MultipartConfig
public class UserController extends HttpServlet {
    @Inject
    private AccountService accountService;
    @Inject
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        long accountId = Long.parseLong(session.getAttribute("accountId").toString());
        try {
            AccountResponse account = accountService.getAccountById(accountId);
            if (account == null) {
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            HttpUtils.setAttribute(req, "account", account);
            String activeTab = req.getParameter("tab");
            if (activeTab == null || activeTab.isEmpty()) {
                activeTab = "profile";
            }
            if ("order-history".equals(activeTab)) {
                List<OrderResponse> orders = orderService.getOrdersByAccountId(accountId);
                if (orders == null) {
                    orders = new ArrayList<>();
                }
                List<Boolean> tamperStatuses = new ArrayList<>();
                for (OrderResponse orderResponse : orders) {
                    tamperStatuses.add(!orderResponse.isDigitallySigned());
                }
                HttpUtils.setAttribute(req, "orders", orders);
                HttpUtils.setAttribute(req, "tamperStatuses", tamperStatuses);
                HttpUtils.setAttribute(req, "ordersCount", orders.size());
            }
            HttpUtils.setAttribute(req, "activeTab", activeTab);
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (Exception e) {
            LogUtils.debug(UserController.class, "Lỗi khi tải thông tin hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi tải thông tin hồ sơ: " + e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long accountId = Long.parseLong(session.getAttribute("accountId").toString());
        String action = req.getParameter("action");

        try {
            if ("updateProfile".equals(action)) {
                String username = req.getParameter("username");
                String email = req.getParameter("email");
                if (username == null || email == null || username.trim().isEmpty() || email.trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(username.trim(), email.trim());
                AccountResponse currentAccount = accountService.getAccountById(accountId);
                if (currentAccount.username().equals(username.trim()) && currentAccount.email().equals(email.trim())) {
                    HttpUtils.setAttribute(req, "message", "Không có thay đổi nào để cập nhật.");
                } else {
                    accountService.updateProfile(accountId, updateProfileRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật hồ sơ thành công.");
                    // Cập nhật session để phản ánh thay đổi
                    AccountResponse updatedAccount = accountService.getAccountById(accountId);
                    session.setAttribute("user", updatedAccount);
                }
            } else if ("changePassword".equals(action)) {
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                if (oldPassword == null || newPassword == null || oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ mật khẩu.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }
                if (oldPassword.equals(newPassword)) {
                    HttpUtils.setAttribute(req, "error", "Mật khẩu mới không được giống mật khẩu cũ.");
                } else {
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPassword.trim(), newPassword.trim());
                    boolean success = accountService.changePassword(accountId, changePasswordRequest);
                    if (success) {
                        HttpUtils.setAttribute(req, "message", "Đổi mật khẩu thành công.");
                    } else {
                        HttpUtils.setAttribute(req, "error", "Mật khẩu cũ không đúng.");
                    }
                }
            } else if ("uploadPublicKey".equals(action)) {
                String publicKeyText = req.getParameter("publicKeyText");
                Part filePart = req.getPart("publicKeyFile");
                String publicKey = extractPublicKeyFromFile(filePart, publicKeyText);
                if (publicKey != null) {
                    UpdatePublicKeyRequest updatePublicKeyRequest = new UpdatePublicKeyRequest(publicKey);
                    accountService.updatePublicKey(accountId, updatePublicKeyRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật khóa công khai thành công.");
                    // Cập nhật session để phản ánh public key mới
                    AccountResponse updatedAccount = accountService.getAccountById(accountId);
                    session.setAttribute("user", updatedAccount);
                } else {
                    HttpUtils.setAttribute(req, "error", "Vui lòng tải lên file hoặc nhập khóa công khai.");
                }
            } else if ("revokePublicKey".equals(action)) {
                accountService.revokePublicKey(accountId);
                HttpUtils.setAttribute(req, "message", "Thu hồi khóa công khai thành công.");
                // Cập nhật session để phản ánh thay đổi
                AccountResponse updatedAccount = accountService.getAccountById(accountId);
                session.setAttribute("user", updatedAccount);
            } else {
                HttpUtils.setAttribute(req, "error", "Hành động không hợp lệ.");
            }
            HttpUtils.setAttribute(req, "account", accountService.getAccountById(accountId));
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (Exception e) {
            LogUtils.debug(UserController.class, "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "account", accountService.getAccountById(accountId));
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }

    private String extractPublicKeyFromFile(Part filePart, String publicKeyText) {
        if (filePart != null && filePart.getSize() > 0) {
            try {
                String fileContent = new String(filePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                String[] lines = fileContent.split("\n");
                StringBuilder keyBuilder = new StringBuilder();
                for (String line : lines) {
                    if (!line.startsWith("-----")) {
                        keyBuilder.append(line.trim());
                    }
                }
                return keyBuilder.toString();
            } catch (Exception e) {
                LogUtils.debug(UserController.class, "Lỗi khi đọc file khóa công khai: " + e.getMessage());
                return null;
            }
        }
        return publicKeyText != null ? publicKeyText.trim() : null;
    }
}