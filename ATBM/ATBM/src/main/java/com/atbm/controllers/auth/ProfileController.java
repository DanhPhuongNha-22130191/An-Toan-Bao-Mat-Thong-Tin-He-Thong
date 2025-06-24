package com.atbm.controllers.auth;

import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.services.AccountService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.inject.spi.CDI;
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

/**
 * Controller xử lý trang hồ sơ cá nhân của người dùng:
 * gồm cập nhật thông tin cá nhân, đổi mật khẩu, cập nhật hoặc thu hồi khóa công khai.
 */
@WebServlet("/profile")
@MultipartConfig
public class ProfileController extends HttpServlet {
    private AccountService accountService;

    /**
     * Hàm khởi tạo các service khi servlet được khởi chạy.
     */
    @Override
    public void init() throws ServletException {
        try {
            accountService = CDI.current().select(AccountService.class).get();
        } catch (Exception e) {
            LogUtils.debug(ProfileController.class, "Khởi tạo ProfileController thất bại: " + e.getMessage());
            throw new ServletException("Khởi tạo ProfileController thất bại", e);
        }
    }

    /**
     * Xử lý yêu cầu GET – hiển thị thông tin hồ sơ người dùng.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Kiểm tra nếu người dùng chưa đăng nhập thì chuyển về login
        if (session == null || session.getAttribute("accountId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        long accountId = Long.parseLong(session.getAttribute("accountId").toString());

        try {
            // Lấy thông tin tài khoản người dùng từ DB
            AccountResponse account = accountService.getAccountById(accountId);
            if (account == null) {
                // Nếu không tìm thấy tài khoản, xóa session và chuyển về login
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            HttpUtils.setAttribute(req, "account", account);

            // Lấy tab đang active để giữ trạng thái giao diện
            String activeTab = req.getParameter("tab");
            if (activeTab == null || activeTab.isEmpty()) {
                activeTab = "profile";
            }
            HttpUtils.setAttribute(req, "activeTab", activeTab);

            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (Exception e) {
            LogUtils.debug(ProfileController.class, "Lỗi khi tải thông tin hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi tải thông tin hồ sơ: " + e.getMessage());
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }

    /**
     * Xử lý yêu cầu POST – thực hiện hành động theo nút người dùng nhấn: cập nhật hồ sơ, đổi mật khẩu, cập nhật khóa, thu hồi khóa.
     */
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
                // Cập nhật tên người dùng và email
                String username = req.getParameter("username");
                String email = req.getParameter("email");

                if (username == null || email == null || username.trim().isEmpty() || email.trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }

                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(username.trim(), email.trim());
                AccountResponse currentAccount = accountService.getAccountById(accountId);

                // Kiểm tra nếu không có thay đổi thì không gọi cập nhật
                if (currentAccount.username().equals(username.trim()) &&
                        currentAccount.email().equals(email.trim())) {
                    HttpUtils.setAttribute(req, "message", "Không có thay đổi nào để cập nhật.");
                } else {
                    accountService.updateProfile(accountId, updateProfileRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật hồ sơ thành công.");
                }

            } else if ("changePassword".equals(action)) {
                // Đổi mật khẩu người dùng
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");

                if (oldPassword == null || newPassword == null ||
                        oldPassword.trim().isEmpty() || newPassword.trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ mật khẩu.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }

                if (oldPassword.equals(newPassword)) {
                    HttpUtils.setAttribute(req, "error", "Mật khẩu mới không được giống mật khẩu cũ.");
                } else {
                    ChangePasswordRequest changePasswordRequest =
                            new ChangePasswordRequest(oldPassword.trim(), newPassword.trim());
                    boolean success = accountService.changePassword(accountId, changePasswordRequest);
                    if (success) {
                        HttpUtils.setAttribute(req, "message", "Đổi mật khẩu thành công.");
                    } else {
                        HttpUtils.setAttribute(req, "error", "Mật khẩu cũ không đúng.");
                    }
                }

            } else if ("uploadPublicKey".equals(action)) {
                // Người dùng cập nhật khóa công khai bằng cách nhập tay hoặc upload file
                String publicKeyText = req.getParameter("publicKeyText");
                Part filePart = req.getPart("publicKeyFile");

                String publicKey = null;

                if (filePart != null && filePart.getSize() > 0) {
                    publicKey = extractPublicKeyFromFile(filePart);
                } else if (publicKeyText != null && !publicKeyText.trim().isEmpty()) {
                    publicKey = publicKeyText.trim();
                }

                if (publicKey != null) {
                    UpdatePublicKeyRequest updatePublicKeyRequest = new UpdatePublicKeyRequest(publicKey);
                    accountService.updatePublicKey(accountId, updatePublicKeyRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật khóa công khai thành công.");
                } else {
                    HttpUtils.setAttribute(req, "error", "Vui lòng tải lên file hoặc nhập khóa công khai.");
                }

            } else if ("revokePublicKey".equals(action)) {
                // Thu hồi khóa công khai
                accountService.revokePublicKey(accountId);
                HttpUtils.setAttribute(req, "message", "Thu hồi khóa công khai thành công.");
            } else {
                HttpUtils.setAttribute(req, "error", "Hành động không hợp lệ.");
            }

            // Sau mỗi hành động, cập nhật lại thông tin tài khoản để hiển thị
            HttpUtils.setAttribute(req, "account", accountService.getAccountById(accountId));
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        } catch (Exception e) {
            LogUtils.debug(ProfileController.class, "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "error", "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
            HttpUtils.setAttribute(req, "account", accountService.getAccountById(accountId));
            HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
        }
    }

    /**
     * Hàm hỗ trợ để trích xuất nội dung khóa công khai từ file upload (.pem).
     * @param filePart file chứa khóa công khai
     * @return nội dung khóa dưới dạng chuỗi Base64
     */
    private String extractPublicKeyFromFile(Part filePart) {
        try {
            String fileContent = new String(filePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            String[] lines = fileContent.split("\n");
            StringBuilder keyBuilder = new StringBuilder();
            for (String line : lines) {
                // Bỏ qua dòng bắt đầu bằng -----BEGIN/END
                if (!line.startsWith("-----")) {
                    keyBuilder.append(line.trim());
                }
            }
            return keyBuilder.toString();
        } catch (Exception e) {
            LogUtils.debug(ProfileController.class, "Lỗi khi đọc file khóa công khai: " + e.getMessage());
            return null;
        }
    }
}