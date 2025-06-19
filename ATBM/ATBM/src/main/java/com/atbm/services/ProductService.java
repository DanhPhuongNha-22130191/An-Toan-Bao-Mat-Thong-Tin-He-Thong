package com.atbm.services;

import com.atbm.dao.brand.BrandDao;
import com.atbm.dao.brand.impl.BrandDaoImpl;
import com.atbm.dao.product.ProductDao;
import com.atbm.dao.product.impl.ProductDaoImpl;
import com.atbm.dao.strap.StrapDao;
import com.atbm.dao.strap.impl.StrapDaoImpl;
import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Product;
import com.atbm.models.entity.Strap;
import com.atbm.models.wrapper.request.AddProductRequest;
import com.atbm.models.wrapper.request.EditProductRequest;
import com.atbm.models.wrapper.request.FilterProductRequest;
import com.atbm.models.wrapper.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductDao productDao;
    private final BrandDao brandDao;
    private final StrapDao strapDao;

    public ProductService() {
        productDao = new ProductDaoImpl();
        brandDao = new BrandDaoImpl();
        strapDao = new StrapDaoImpl();
    }

    //Lấy danh sách tất cả sản phẩm
    public List<ProductResponse> getProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = productDao.getProducts();
        for (Product product : products) {
            productResponses.add(createProductResponse(product));
        }
        return productResponses;
    }

    //Lấy sản phẩm theo id
    public ProductResponse getProductById(long productId) {
        Product product = productDao.getProductById(productId);
        return createProductResponse(product);
    }

    private ProductResponse createProductResponse(Product product) {
        Strap strap = strapDao.getStrapById(product.getStrapId());
        Brand brand = brandDao.getBrandById(product.getBrandId());
        return new ProductResponse(product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImage(),
                strap,
                brand,
                product.isWaterResistance(),
                product.getSize());
    }

    public List<ProductResponse> filterProduct(FilterProductRequest filterProductRequest) {
        List<Product> products = productDao.getProducts();
        List<Product> productFiltered = products.stream().filter(product ->
                filterProductRequest.brandsId().contains(product.getBrandId())
                        && filterProductRequest.strapsId().contains(product.getStrapId())
                        && product.getPrice() >= filterProductRequest.minPrice()
                        && product.getPrice() <= filterProductRequest.maxPrice()

        ).toList();
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : productFiltered) {
            productResponses.add(createProductResponse(product));
        }
        return productResponses;
    }

    // Xóa sản phầm theo id
    public boolean deleteProductById(long productId) {
        return productDao.delete(productId);
    }

    // Taọ sản phẩm mới
    public boolean addProduct(AddProductRequest addProductRequest) {
        Product product = new Product(
                0L,
                addProductRequest.name(),
                addProductRequest.price(),
                addProductRequest.description(),
                addProductRequest.stock(),
                addProductRequest.image(),
                false,
                addProductRequest.size(),
                addProductRequest.waterResistance(),
                addProductRequest.brandId(),
                addProductRequest.strapId()
        );
        return productDao.insert(product);
    }
    // Cập nhật sản phẩm theo id
    public boolean editProduct(EditProductRequest editRequest) {
        Product existingProduct = productDao.getProductById(editRequest.productId());
        if (existingProduct == null) {
            return false;
        }
        existingProduct.setName(editRequest.name());
        existingProduct.setPrice(editRequest.price());
        existingProduct.setDescription(editRequest.description());
        existingProduct.setStock(editRequest.stock());
        existingProduct.setBrandId(editRequest.brandId());
        if (editRequest.image() != null && editRequest.image().length > 0) {
            existingProduct.setImage(editRequest.image());
        }
        return productDao.update(existingProduct);
    }

    public static void main(String[] args) {
        ProductService productService = new ProductService();

        // Giả lập ảnh nhị phân mới (ví dụ một mảng byte đơn giản)
        byte[] newImageBytes = new byte[] { (byte)0x89, (byte)0x50, (byte)0x4E, (byte)0x47 }; // ví dụ dữ liệu ảnh PNG đầu file

        // Giả lập dữ liệu đầu vào để chỉnh sửa sản phẩm đã tồn tại
        EditProductRequest editRequest = new EditProductRequest(
                1L, // productId cần sửa, giả sử sản phẩm có ID = 1
                "AAAAA",
                1700000.0,
                "Đồng hồ chống nước, dây da thật, phiên bản mới",
                15,
                newImageBytes, // ảnh nhị phân mới
                1L // brandId giả định
        );

        boolean result = productService.editProduct(editRequest);
        System.out.println("Cập nhật sản phẩm " + (result ? "THÀNH CÔNG" : "THẤT BẠI"));
    }




}
