package com.atbm.controllers.user.update;

import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
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
import java.security.NoSuchAlgorithmException;

@WebServlet("/user/update")
@MultipartConfig
public class UserController extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = new AccountService();
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
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(username.trim(), email.trim());
                // Kiểm tra không thay đổi
                AccountResponse currentAccount = accountService.getUserInfo(accountId);
                if (currentAccount.username().equals(username.trim()) && currentAccount.email().equals(email.trim())) {
                    HttpUtils.setAttribute(req, "message", "Không có thay đổi nào để cập nhật.");
                } else {
                    accountService.updateProfile(accountId, updateProfileRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật hồ sơ thành công.");
                }
            } else if ("changePassword".equals(action)) {
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPassword.trim(), newPassword.trim());
                accountService.changePassword(accountId, changePasswordRequest);
                HttpUtils.setAttribute(req, "message", "Đổi mật khẩu thành công.");
            } else if ("uploadPublicKey".equals(action)) {
                String publicKeyText = req.getParameter("publicKeyText");
                Part filePart = req.getPart("publicKeyFile");
                String publicKey = extractPublicKeyFromFile(filePart, publicKeyText);
                UpdatePublicKeyRequest updatePublicKeyRequest = new UpdatePublicKeyRequest(publicKey);
                accountService.updatePublicKey(accountId, updatePublicKeyRequest);
                HttpUtils.setAttribute(req, "message", "Cập nhật khóa công khai thành công.");
            } else if ("revokePublicKey".equals(action)) {
                accountService.revokePublicKey(accountId);
                HttpUtils.setAttribute(req, "message", "Thu hồi khóa công khai thành công.");
            } else {
                HttpUtils.setAttribute(req, "error", "Hành động không hợp lệ.");
            }

            HttpUtils.setAttribute(req, "account", accountService.getUserInfo(accountId));
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (RuntimeException | NoSuchAlgorithmException e) {
            HttpUtils.setAttribute(req, "error", e.getMessage());
            HttpUtils.setAttribute(req, "account", accountService.getUserInfo(accountId));
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