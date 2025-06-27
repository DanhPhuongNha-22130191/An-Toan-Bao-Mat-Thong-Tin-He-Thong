package com.atbm.services;

import com.atbm.dao.brand.BrandDao;
import com.atbm.dao.product.ProductDao;
import com.atbm.dao.strap.StrapDao;
import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Product;
import com.atbm.models.entity.Strap;
import com.atbm.models.wrapper.request.AddProductRequest;
import com.atbm.models.wrapper.request.EditProductRequest;
import com.atbm.models.wrapper.request.FilterProductRequest;
import com.atbm.models.wrapper.response.ProductResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {
    private  ProductDao productDao;
    private  BrandDao brandDao;
    private  StrapDao strapDao;

    @Inject
    public ProductService(ProductDao productDao, BrandDao brandDao, StrapDao strapDao) {
        this.productDao = productDao;
        this.brandDao = brandDao;
        this.strapDao = strapDao;
    }

    public ProductService() {
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

    // Tạo sản phẩm mới mà KHÔNG cần trường waterResistance từ request
    public boolean addProduct(AddProductRequest addProductRequest) {
        Product product = new Product(
                0L,
                addProductRequest.getName(),
                addProductRequest.getPrice(),
                addProductRequest.getDescription(),
                addProductRequest.getStock(),
                addProductRequest.getImage(),
                false, // isTrending
                addProductRequest.getSize(),
                false,
                addProductRequest.getBrandId(),
                addProductRequest.getStrapId()
        );
        return productDao.insert(product);
    }


    // Cập nhật sản phẩm theo id
    public boolean editProduct(EditProductRequest editRequest) {
        Product existingProduct = productDao.getProductById(editRequest.getProductId());
        if (existingProduct == null) {
            return false;
        }
        existingProduct.setName(editRequest.getName());
        existingProduct.setPrice(editRequest.getPrice());
        existingProduct.setDescription(editRequest.getDescription());
        existingProduct.setStock(editRequest.getStock());
        existingProduct.setBrandId(editRequest.getBrandId());
        if (editRequest.getImage() != null && editRequest.getImage().length > 0) {
            existingProduct.setImage(editRequest.getImage());
        }
        return productDao.update(existingProduct);
    }

    public List<Brand> getBrands() {
        return brandDao.getBrands();
    }
    public List<Strap> getStraps() {
        return strapDao.getStraps();
    }
}
