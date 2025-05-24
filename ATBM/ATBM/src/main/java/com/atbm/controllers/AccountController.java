package com.atbm.controllers;

import com.atbm.mail.EmailUtil;
import com.atbm.models.Account;
import com.atbm.services.AccountService;
import com.atbm.dto.AccountDTO;
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
import java.util.Random;

@WebServlet("/user/account")
public class AccountController extends HttpServlet {
    private AccountService accountService = new AccountService();
    private static final String RECAPTCHA_SECRET_KEY = "6LcooTwrAAAAADsuwNgS3T4IsD9Cu1jmmSbJ5p1Y";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("register".equals(action)) {
            register(req, resp);
        } else if ("login".equals(action)) {
            login(req, resp);
        } else if ("updateProfile".equals(action)) {
            updateProfile(req, resp);
        } else if ("changePassword".equals(action)) {
            changePassword(req, resp);
        } else if ("forgotPassword".equals(action)) {
            forgotPassword(req, resp);
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

        boolean success = accountService.register(username, password, email);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp?success=registered");
        } else {
            req.setAttribute("error", "Tên tài khoản đã tồn tại.");
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
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString().contains("\"success\": true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Account account = accountService.login(username, password);
        if (account != null) {
            AccountDTO accountDTO = new AccountDTO(account.getAccountId(), account.getUsername(), account.getEmail());
            HttpSession session = req.getSession();
            session.setAttribute("user", accountDTO);
            resp.sendRedirect("/product?action=shop");

        } else {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");

        if (user != null) {
            String newUsername = request.getParameter("username");
            String newEmail = request.getParameter("email");

            if (newUsername.equals(user.getUsername()) && newEmail.equals(user.getEmail())) {
                request.setAttribute("message", "Không có thay đổi nào để cập nhật.");
                request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
                return;
            }

            user.setUsername(newUsername);
            user.setEmail(newEmail);

            boolean updated = accountService.updateProfile(user);
            if (updated) {
                session.setAttribute("user", user);
                request.setAttribute("message", "Cập nhật thông tin thành công!");
            } else {
                request.setAttribute("error", "Lỗi cập nhật thông tin.");
            }
            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");

        if (user != null) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");

            if (!accountService.checkPassword(user.getAccountId(), oldPassword)) {
                request.setAttribute("error", "Mật khẩu cũ không đúng!");
                request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
                return;
            }

            if (oldPassword.equals(newPassword)) {
                request.setAttribute("error", "Mật khẩu mới không được giống mật khẩu cũ.");
                request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
                return;
            }

            boolean updated = accountService.updatePassword(user.getAccountId(), newPassword);
            if (updated) {
                request.setAttribute("message", "Mật khẩu đã được cập nhật thành công.");
            } else {
                request.setAttribute("error", "Lỗi khi cập nhật mật khẩu.");
            }
            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
        }
    }

    private void forgotPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        Account account = accountService.getAccountByEmail(email);

        if (account != null) {
            String newPassword = generateRandomPassword();

            boolean updated = accountService.updatePassword(account.getAccountId(), newPassword);

            if (updated) {
                boolean emailSent = EmailUtil.sendEmail(email, "Khôi phục mật khẩu",
                        "Mật khẩu mới của bạn là: " + newPassword);

                if (emailSent) {
                    req.setAttribute("message", "Mật khẩu mới đã được gửi đến email của bạn.");
                } else {
                    req.setAttribute("error", "Gửi email thất bại. Vui lòng thử lại sau.");
                }
            } else {
                req.setAttribute("error", "Không thể cập nhật mật khẩu. Vui lòng thử lại.");
            }
        } else {
            req.setAttribute("error", "Email không tồn tại trong hệ thống.");
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            logout(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
    }
}