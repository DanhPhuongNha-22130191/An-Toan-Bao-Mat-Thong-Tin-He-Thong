package com.atbm.controllers.user.update;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.mapper.FormMapper;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/user/update")
@MultipartConfig
public class UserController extends BaseController {
    @Inject
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long accountId = getAccountId(req);
            AccountResponse account = accountService.getAccountById(accountId);
            if (account == null) {
                req.getSession().invalidate();
                HttpUtils.sendRedirect(req, resp, "/login");
                return;
            }
            HttpUtils.setAttribute(req, "account", account);
            String activeTab = req.getParameter("tab");
            if (activeTab == null || activeTab.isEmpty()) {
                activeTab = "account-settings";
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
        try {
            long accountId = getAccountId(req);
            String action = req.getParameter("action");
            AccountResponse account = accountService.getAccountById(accountId);
            HttpUtils.setAttribute(req, "account", account);

            if ("updateProfile".equals(action)) {
                UpdateProfileRequest updateProfileRequest = FormMapper.bind(req.getParameterMap(), UpdateProfileRequest.class);
                if (updateProfileRequest.getUsername() == null || updateProfileRequest.getEmail() == null ||
                        updateProfileRequest.getUsername().trim().isEmpty() || updateProfileRequest.getEmail().trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }
                if (account.getUsername().equals(updateProfileRequest.getUsername().trim()) &&
                        account.getEmail().equals(updateProfileRequest.getEmail().trim())) {
                    HttpUtils.setAttribute(req, "message", "Không có thay đổi nào để cập nhật.");
                } else {
                    accountService.updateProfile(accountId, updateProfileRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật hồ sơ thành công.");
                    updateSession(req, accountId);
                }
            } else if ("changePassword".equals(action)) {
                ChangePasswordRequest changePasswordRequest = FormMapper.bind(req.getParameterMap(), ChangePasswordRequest.class);
                if (changePasswordRequest.getOldPassword() == null || changePasswordRequest.getNewPassword() == null ||
                        changePasswordRequest.getOldPassword().trim().isEmpty() || changePasswordRequest.getNewPassword().trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ mật khẩu.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }
                if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
                    HttpUtils.setAttribute(req, "error", "Mật khẩu mới không được giống mật khẩu cũ.");
                } else {
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
                String publicKeyRaw = extractPublicKeyFromFile(filePart, publicKeyText);
                String publicKey = normalizePublicKey(publicKeyRaw);

                if (publicKey != null) {
                    UpdatePublicKeyRequest updatePublicKeyRequest = new UpdatePublicKeyRequest(publicKey);
                    accountService.updatePublicKey(accountId, updatePublicKeyRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật khóa công khai thành công.");
                    updateSession(req, accountId);
                } else {
                    HttpUtils.setAttribute(req, "error", "Vui lòng tải lên file hoặc nhập khóa công khai.");
                }
            } else if ("revokePublicKey".equals(action)) {
                accountService.revokePublicKey(accountId);
                HttpUtils.setAttribute(req, "message", "Thu hồi khóa công khai thành công.");
                updateSession(req, accountId);
            } else {
                HttpUtils.setAttribute(req, "error", "Hành động không hợp lệ.");
            }
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (Exception e) {
            LogUtils.debug(UserController.class, "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }

    private void updateSession(HttpServletRequest req, long accountId) {
        AccountResponse updatedAccount = accountService.getAccountById(accountId);
        HttpSession session = req.getSession();
        session.setAttribute("user", updatedAccount);
    }

    private String extractPublicKeyFromFile(Part filePart, String publicKeyText) {
        try {
            if (filePart != null && filePart.getSize() > 0) {
                String fileContent = new String(filePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                fileContent = fileContent.strip(); // tốt hơn trim()
                if (fileContent.startsWith("\uFEFF")) {  // BOM check
                    fileContent = fileContent.substring(1);
                }
                return fileContent;
            }
        } catch (Exception e) {
            LogUtils.debug(UserController.class, "Lỗi khi đọc file khóa công khai: " + e.getMessage());
        }
        return publicKeyText != null ? publicKeyText.strip() : null;
    }

    private String normalizePublicKey(String rawKey) {
        if (rawKey == null || rawKey.isEmpty()) return null;

        // Loại bỏ header/footer và whitespace
        String keyContent = rawKey.replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", ""); // loại bỏ tất cả whitespace

        // Kiểm tra nếu key content rỗng
        if (keyContent.isEmpty()) return null;

        // Format lại theo chuẩn PEM với dòng 64 ký tự
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN PUBLIC KEY-----");
        sb.append(System.lineSeparator()); // Sử dụng line separator của hệ thống

        // Chia key content thành các dòng 64 ký tự
        for (int i = 0; i < keyContent.length(); i += 64) {
            int end = Math.min(i + 64, keyContent.length());
            sb.append(keyContent.substring(i, end));
            sb.append(System.lineSeparator());
        }

        sb.append("-----END PUBLIC KEY-----");
        return sb.toString();
    }
}