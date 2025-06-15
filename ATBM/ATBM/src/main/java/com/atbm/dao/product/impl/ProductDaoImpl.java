package com.atbm.dao.product.impl;

import com.atbm.dao.product.ProductDao;
import com.atbm.models.entity.Product;
import com.atbm.utils.ExecuteSQLUtils;
import com.atbm.utils.LogUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private final String PRODUCT_ID = "productId";
    private final String NAME = "name";
    private final String PRICE = "price";
    private final String DESCRIPTION = "description";
    private final String STOCK = "stock";
    private final String IMAGE = "image";
    private final String TRENDING = "isTrending";
    private final String SIZE = "size";
    private final String WATER_RESISTANCE = "waterResistance";
    private final String BRAND_ID = "brandId";
    private final String STRAP_ID = "strapId";
    private final String DELETED = "isDeleted";

    @Override
    public Product getProductById(long productId) {
        String query = "SELECT * FROM Product WHERE productId = ?";
        try {
            return createProduct(ExecuteSQLUtils.ExecuteQuery(query, productId));
        } catch (SQLException e) {
            LogUtils.debug(ProductDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Product lỗi");
        }
    }

    @Override
    public List<Product> getProducts() {
        String query = "SELECT * FROM Product WHERE isDeleted = false ORDER BY price ";
        try (ResultSet rs = ExecuteSQLUtils.ExecuteQuery(query)) {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(createProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            LogUtils.debug(ProductDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Product lỗi");
        }
    }

    @Override
    public boolean insert(Product product) {
        String query = "INSERT INTO Product (name, price, description, stock, image, isTrending, size, waterResistance, brandId, strapId, isDeleted) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        return ExecuteSQLUtils.executeUpdate(
                query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImage(),
                product.isTrending(),
                product.getSize(),
                product.isWaterResistance(),
                product.getBrandId(),
                product.getStrapId(),
                product.isDeleted()
        );
    }

    @Override
    public boolean update(Product product) {
        String query = "UPDATE Product SET name=?, price=?, description=?, stock=?, image=?, isTrending=?, size=?, waterResistance=?, brandId=?, strapId=?, isDeleted=? WHERE productId=?";
        return ExecuteSQLUtils.executeUpdate(
                query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImage(),
                product.isTrending(),
                product.getSize(),
                product.isWaterResistance(),
                product.getBrandId(),
                product.getStrapId(),
                product.isDeleted(),
                product.getProductId()
        );
    }

    @Override
    public boolean delete(long productId) {
        String query = "UPDATE Product SET isDeleted=true WHERE productId=?";
        return ExecuteSQLUtils.executeUpdate(query, productId);
    }


    private Product createProduct(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Product(
                    resultSet.getLong(PRODUCT_ID),
                    resultSet.getString(NAME),
                    resultSet.getDouble(PRICE),
                    resultSet.getString(DESCRIPTION),
                    resultSet.getInt(STOCK),
                    resultSet.getBytes(IMAGE),
                    resultSet.getBoolean(TRENDING),
                    resultSet.getDouble(SIZE),
                    resultSet.getBoolean(WATER_RESISTANCE),
                    resultSet.getLong(BRAND_ID),
                    resultSet.getLong(STRAP_ID)
            );
        }
        return null;
    }
}