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
}
