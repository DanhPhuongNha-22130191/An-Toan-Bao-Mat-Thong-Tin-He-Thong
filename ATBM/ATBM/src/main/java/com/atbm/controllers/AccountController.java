package com.atbm.controllers;

import com.atbm.mail.EmailUtil;
import com.atbm.services.AccountService;
import dto.AccountDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Random;
@WebServlet("/user/account")
public class AccountController extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        this.doGet(req, resp);
        if ("register".equals(action)) {
            register(req, resp);
        } else if ("login".equals(action)) {
            login(req, resp);
        } else if ("updateProfile".equals(action)) {
            updateProfile(req, resp);
        } else if ("changePassword".equals(action)) {
            changePassword(req, resp);
        }else if ("forgotPassword".equals(action)) {  
            forgotPassword(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        boolean success = accountService.register(username, password, email);
        if (success) {
            resp.sendRedirect("login.jsp?success=registered");
        } else {
            req.setAttribute("error", "Tên tài khoản đã tồn tại.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        models.Account account = accountService.login(username, password);
        if (account != null) {
            AccountDTO accountDTO = new AccountDTO(account.getAccountId(), account.getUsername(), account.getEmail());
            HttpSession session = req.getSession();
            session.setAttribute("user", accountDTO);
            resp.sendRedirect("home.jsp");
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

            // Kiểm tra nếu dữ liệu không thay đổi thì không làm gì cả
            if (newUsername.equals(user.getUsername()) && newEmail.equals(user.getEmail())) {
                request.setAttribute("message", "Không có thay đổi nào để cập nhật.");
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Cập nhật thông tin nếu có thay đổi
            user.setUsername(newUsername);
            user.setEmail(newEmail);

            boolean updated = accountService.updateProfile(user);
            if (updated) {
                session.setAttribute("user", user);
                request.setAttribute("message", "Cập nhật thông tin thành công!");
            } else {
                request.setAttribute("error", "Lỗi cập nhật thông tin.");
            }
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        AccountDTO user = (AccountDTO) session.getAttribute("user");

        if (user != null) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");

            // Kiểm tra mật khẩu cũ có đúng không
            if (!accountService.checkPassword(user.getAccountId(), oldPassword)) {
                request.setAttribute("error", "Mật khẩu cũ không đúng!");
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Kiểm tra nếu mật khẩu mới giống mật khẩu cũ
            if (oldPassword.equals(newPassword)) {
                request.setAttribute("error", "Mật khẩu mới không được giống mật khẩu cũ.");
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }

            // Cập nhật mật khẩu
            boolean updated = accountService.updatePassword(user.getAccountId(), newPassword);
            if (updated) {
                request.setAttribute("message", "Mật khẩu đã được cập nhật thành công.");
            } else {
                request.setAttribute("error", "Lỗi khi cập nhật mật khẩu.");
            }
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }
    private void forgotPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        
        // Kiểm tra xem email có tồn tại trong hệ thống không
        models.Account account = accountService.getAccountByEmail(email);
        
        if (account != null) {
            // Tạo mật khẩu mới ngẫu nhiên
            String newPassword = generateRandomPassword();
            
            // Cập nhật mật khẩu mới trong cơ sở dữ liệu
            boolean updated = accountService.updatePassword(account.getAccountId(), newPassword);
            
            if (updated) {
                // Gửi email chứa mật khẩu mới
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
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    // Hàm tạo mật khẩu ngẫu nhiên
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
        resp.sendRedirect("login.jsp");
    }
}
