package com.atbm.dao.product;

import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Product;

import java.util.List;

public interface ProductDao {
    String TABLE_NAME = "Product";
    String PRODUCT_ID = "productId";
    String NAME = "name";
    String PRICE = "price";
    String DESCRIPTION = "description";
    String STOCK = "stock";
    String IMAGE = "image";
    String TRENDING = "isTrending";
    String SIZE = "size";
    String WATER_RESISTANCE = "waterResistance";
    String BRAND_ID = "brandId";
    String STRAP_ID = "strapId";
    String DELETED = "isDeleted";

    Product getProductById(long productId);

    boolean insert(Product product);

    boolean update(Product product);

    boolean delete(long productId);

    List<Product> getProducts();

}
