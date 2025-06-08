package com.atbm.controllers;

import com.atbm.models.Voucher;
import com.atbm.services.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/admin/voucher")
public class VoucherAdminVoucher extends HttpServlet {
    private VoucherService voucherService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Override
    public void init() throws ServletException {
        voucherService = new VoucherService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            // Hiển thị thông báo thành công/lỗi nếu có
            String message = request.getParameter("message");
            String error = request.getParameter("error");
            if (message != null) {
                request.setAttribute("message", message);
            }
            if (error != null) {
                request.setAttribute("error", error);
            }

            List<Voucher> voucherList = voucherService.getAll();

            int totalVouchers = voucherList.size();
            int activeVouchers = 0;
            int expiredVouchers = 0;
            int remainingVouchers = 0;

            for (Voucher voucher : voucherList) {
                if (voucher.isValid()) {
                    activeVouchers++;
                }
                if (voucher.isExpired()) {
                    expiredVouchers++;
                }
                if (voucher.hasRemainingQuantity()) {
                    remainingVouchers++;
                }
            }
            request.setAttribute("voucherList", voucherList);
            request.setAttribute("totalVouchers", totalVouchers);
            request.setAttribute("activeVouchers", activeVouchers);
            request.setAttribute("expiredVouchers", expiredVouchers);
            request.setAttribute("usedVouchers", totalVouchers - remainingVouchers);

            request.getRequestDispatcher("/views/voucherAdmin.jsp").forward(request, response);

        } catch (Exception e) {
            handleError(request, response, "Error loading vouchers: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    handleAddVoucher(request, response);
                    break;
                case "edit":
                    handleEditVoucher(request, response);
                    break;
                case "delete":
                    handleDeleteVoucher(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    private void handleAddVoucher(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String code = request.getParameter("code");
        // Kiểm tra mã voucher tồn tại
        if (voucherService.existsByCode(code)) {
            throw new IllegalArgumentException("Mã voucher đã tồn tại");
        }

        String name = request.getParameter("name");
        double percentDecrease = Double.parseDouble(request.getParameter("percentDecrease"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        java.util.Date parsedDate = dateFormat.parse(request.getParameter("expirationTime"));
        Date expirationTime = new Date(parsedDate.getTime());

        validateVoucherInput(code, name, percentDecrease, quantity, expirationTime);

        Voucher voucher = new Voucher(code, expirationTime, percentDecrease, name, quantity);
        if (voucherService.insert(voucher)) {
            String message = URLEncoder.encode("Thêm voucher thành công", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/voucher?message=" + message);
        } else {
            throw new Exception("Không thể thêm voucher");
        }
    }

    private void handleEditVoucher(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        long voucherId = Long.parseLong(request.getParameter("voucherId"));
        String code = request.getParameter("code");


        String name = request.getParameter("name");
        double percentDecrease = Double.parseDouble(request.getParameter("percentDecrease"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        java.util.Date parsedDate = dateFormat.parse(request.getParameter("expirationTime"));
        Date expirationTime = new Date(parsedDate.getTime());

        validateVoucherInput(code, name, percentDecrease, quantity, expirationTime);

        Voucher voucher = new Voucher(voucherId, code, expirationTime, percentDecrease, name, quantity);
        if (voucherService.update(voucher)) {
            String message = URLEncoder.encode("Cập nhật voucher thành công", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/admin/voucher?message=" + message);
        } else {
            throw new Exception("Không thể cập nhật voucher");
        }
    }

    private void handleDeleteVoucher(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            long voucherId = Long.parseLong(request.getParameter("voucherId"));
            if (voucherService.delete(voucherId)) {
                String message = URLEncoder.encode("Xóa voucher thành công", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/admin/voucher?message=" + message);
            } else {
                String message = URLEncoder.encode("Không thể xóa voucher", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/admin/voucher?error="+message);
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/voucher?error=" + e.getMessage());
        }
    }

    private void validateVoucherInput(String code, String name, double percentDecrease,
                                      int quantity, Date expirationTime) throws IllegalArgumentException {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã voucher không được để trống");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên voucher không được để trống");
        }
        if (percentDecrease <= 0 || percentDecrease > 100) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 1-100");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }

        Date now = new Date(System.currentTimeMillis());
        if (expirationTime.before(now)) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau thời điểm hiện tại");
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/views/voucherAdmin.jsp").forward(request, response);
    }
}