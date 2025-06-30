package com.atbm.controllers.admin;

import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Strap;
import com.atbm.models.wrapper.request.AddProductRequest;
import com.atbm.models.wrapper.request.EditProductRequest;
import com.atbm.models.wrapper.response.ProductResponse;
import com.atbm.services.CartService;
import com.atbm.services.ProductService;
import com.atbm.utils.HttpUtils;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/product", "/admin/product/*"})
@MultipartConfig
public class ProductController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService =   CDI.current().select(ProductService.class).get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        List<ProductResponse> listProducts = productService.getProducts();
        List<Brand> listBrands = productService.getBrands();
        List<Strap> listStraps = productService.getStraps();

        HttpUtils.setAttribute(req, "products", listProducts);
        HttpUtils.setAttribute(req, "brands", listBrands);
        HttpUtils.setAttribute(req, "straps", listStraps);

        HttpUtils.dispatcher(req, resp, "/views/productAdmin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleAddProduct(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] parts = req.getPathInfo().split("/");
        long productId = Long.parseLong(parts[2]);
        handleEditProduct(req, resp, productId);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        long productId = Long.parseLong(pathInfo.substring(1));
        productService.deleteProductById(productId);
    }


    private void handleAddProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int stock = Integer.parseInt(req.getParameter("stock"));
        long brandId = Long.parseLong(req.getParameter("brandId"));
        long strapId = Long.parseLong(req.getParameter("strapId"));
        double size = Double.parseDouble(req.getParameter("size"));
        boolean waterResistance = Boolean.parseBoolean(req.getParameter("waterResistance"));

        byte[] imageBytes = null;
        Part imagePart = req.getPart("image");
        if (imagePart != null && imagePart.getSize() > 0) {
            try (InputStream is = imagePart.getInputStream()) {
                imageBytes = is.readAllBytes();
            }
        }

        productService.addProduct(new AddProductRequest(
                name, price, description, stock, imageBytes, size, brandId, strapId, waterResistance
        ));
        HttpUtils.sendRedirect(req, resp, "/admin/product");
    }

    private void handleEditProduct(HttpServletRequest req, HttpServletResponse resp, long productId) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int stock = Integer.parseInt(req.getParameter("stock"));
        long brandId = Long.parseLong(req.getParameter("brandId"));

        byte[] imageBytes = null;
        Part imagePart = req.getPart("image");
        if (imagePart != null && imagePart.getSize() > 0) {
            try (InputStream is = imagePart.getInputStream()) {
                imageBytes = is.readAllBytes();
            }
        }

        EditProductRequest editRequest = new EditProductRequest(
                productId, name, price, description, stock, imageBytes, brandId
        );

        productService.editProduct(editRequest);
        HttpUtils.sendRedirect(req, resp, "/admin/product");
    }


}