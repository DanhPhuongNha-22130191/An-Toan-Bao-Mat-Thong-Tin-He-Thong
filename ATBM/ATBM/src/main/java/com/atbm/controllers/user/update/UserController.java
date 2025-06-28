package com.atbm.controllers.user.update;

import com.atbm.config.BaseController;
import com.atbm.models.wrapper.request.ChangePasswordRequest;
import com.atbm.models.wrapper.request.UpdateProfileRequest;
import com.atbm.models.wrapper.request.UpdatePublicKeyRequest;
import com.atbm.models.wrapper.response.AccountResponse;
import com.atbm.models.wrapper.response.OrderResponse;
import com.atbm.services.AccountService;
import com.atbm.services.OrderService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user/update")
@MultipartConfig
public class UserController extends BaseController {
    @Inject
    private AccountService accountService;
    @Inject
    private OrderService orderService;

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
        try {
            long accountId = getAccountId(req);
            String action = req.getParameter("action");
            AccountResponse account = accountService.getAccountById(accountId);
            HttpUtils.setAttribute(req, "account", account);

            if ("updateProfile".equals(action)) {
                UpdateProfileRequest updateProfileRequest = FormMapper.bind(req.getParameterMap(), UpdateProfileRequest.class);
                if (updateProfileRequest.username() == null || updateProfileRequest.email() == null ||
                        updateProfileRequest.username().trim().isEmpty() || updateProfileRequest.email().trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ thông tin.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }
                if (account.getUsername().equals(updateProfileRequest.username().trim()) &&
                        account.getEmail().equals(updateProfileRequest.email().trim())) {
                    HttpUtils.setAttribute(req, "message", "Không có thay đổi nào để cập nhật.");
                } else {
                    accountService.updateProfile(accountId, updateProfileRequest);
                    HttpUtils.setAttribute(req, "message", "Cập nhật hồ sơ thành công.");
                    updateSession(req, accountId);
                }
            } else if ("changePassword".equals(action)) {
                ChangePasswordRequest changePasswordRequest = FormMapper.bind(req.getParameterMap(), ChangePasswordRequest.class);
                if (changePasswordRequest.oldPassword() == null || changePasswordRequest.newPassword() == null ||
                        changePasswordRequest.oldPassword().trim().isEmpty() || changePasswordRequest.newPassword().trim().isEmpty()) {
                    HttpUtils.setAttribute(req, "error", "Vui lòng nhập đầy đủ mật khẩu.");
                    HttpUtils.dispatcher(req, resp, "/views/profile.jsp");
                    return;
                }
                if (changePasswordRequest.oldPassword().equals(changePasswordRequest.newPassword())) {
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
                String publicKey = extractPublicKeyFromFile(filePart, publicKeyText);
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
        if (filePart != null && filePart.getSize() > 0) {
            try {
                String fileContent = new String(filePart.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                return fileContent.trim();
            } catch (Exception e) {
                LogUtils.debug(UserController.class, "Lỗi khi đọc file khóa công khai: " + e.getMessage());
                return null;
            }
        }
        return publicKeyText != null ? publicKeyText.trim() : null;
    }

}