package com.atbm.controllers;

import com.atbm.models.Product;
import com.atbm.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/admin/editProduct")
@MultipartConfig // Bắt buộc để xử lý file upload
public class ProductEditAdminCtrl extends HttpServlet {
    private final ProductService productService = new ProductService();

    // Đường dẫn thư mục lưu ảnh bên ngoài project
    private static final String EXTERNAL_IMAGE_UPLOAD_DIR = "D:/ATBM-Uploads/product-images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        int stock = Integer.parseInt(request.getParameter("stock"));
        boolean haveTrending = Boolean.parseBoolean(request.getParameter("haveTrending"));
        double size = Double.parseDouble(request.getParameter("size"));
        boolean waterResistance = Boolean.parseBoolean(request.getParameter("waterResistance"));
        int brandId = Integer.parseInt(request.getParameter("brandId"));
        int strapId = Integer.parseInt(request.getParameter("strapId"));
        int stateId = Integer.parseInt(request.getParameter("stateId"));
        boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));

        // Xử lý ảnh upload
        Part imagePart = request.getPart("image");
        String imageFileName = "";

        if (imagePart != null && imagePart.getSize() > 0) {
            imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

            // Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(EXTERNAL_IMAGE_UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Đường dẫn đầy đủ tới ảnh được lưu
            String imageFullPath = EXTERNAL_IMAGE_UPLOAD_DIR + File.separator + imageFileName;
            imagePart.write(imageFullPath);

            System.out.println("Ảnh mới đã được lưu tại: " + imageFullPath);
        } else {
            // Không upload ảnh mới → lấy ảnh cũ từ DB
            imageFileName = productService.getImageByProductId(id);
        }

        // Tạo object product và update DB
        Product product = new Product(id, name, price, description, stock, imageFileName,
                haveTrending, size, waterResistance, brandId, strapId, stateId, isDeleted);

        productService.update(product);
        System.out.println("Đã cập nhật product có tên: " + product.getName());
        System.out.println(product);

        response.sendRedirect("/ATBM/admin/product");
    }
}
