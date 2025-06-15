package com.atbm.dao.product;

import com.atbm.models.entity.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(long productId);

    boolean insert(Product product);

    boolean update(Product product);

    boolean delete(long productId);

    List<Product> getProducts();

}
