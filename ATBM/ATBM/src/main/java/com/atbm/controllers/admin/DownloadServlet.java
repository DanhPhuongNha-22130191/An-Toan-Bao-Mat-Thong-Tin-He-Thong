package com.atbm.controllers.admin;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Đường dẫn thực tế của file zip
        String filePath = getServletContext().getRealPath("/files/ToolInstaller.zip");
        File file = new File(filePath);

        if (!file.exists()) {
            response.setContentType("text/html");
            response.getWriter().println("File không tồn tại.");
            return;
        }

        // Thiết lập header phản hồi để trình duyệt hiểu là file tải về
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLengthLong(file.length());

        // Đọc file và ghi vào output stream
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
