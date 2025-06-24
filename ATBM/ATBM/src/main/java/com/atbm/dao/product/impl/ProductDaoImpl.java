package com.atbm.dao.product.impl;

import com.atbm.dao.product.ProductDao;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Product;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductDaoImpl implements ProductDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public ProductDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }

    @Override
    public Product getProductById(long productId) {
        String query = "SELECT * FROM Product WHERE productId = ?";
        try {
            return createProduct(executeSQLHelper.executeQuery(query, productId));
        } catch (SQLException e) {
            LogUtils.debug(ProductDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Product lỗi");
        }
    }

    @Override
    public List<Product> getProducts() {
        String query = "SELECT * FROM Product WHERE isDeleted = 0 ORDER BY price ";
        try (ResultSet rs = executeSQLHelper.executeQuery(query)) {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = createProduct(rs);
                if (product != null) {
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException e) {
            LogUtils.debug(ProductDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Product lỗi");
        }
    }

    @Override
    public boolean insert(Product product) {
        List<String> fieldNames = List.of(NAME, PRICE, DESCRIPTION, STOCK, IMAGE, TRENDING, SIZE, WATER_RESISTANCE, BRAND_ID, STRAP_ID, DELETED);
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, fieldNames);
        return executeSQLHelper.executeUpdate(
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
        List<String> updateFields = List.of(NAME, PRICE, DESCRIPTION, STOCK, IMAGE, TRENDING, SIZE, WATER_RESISTANCE, BRAND_ID, STRAP_ID, DELETED);
        List<String> conditionFields = List.of(PRODUCT_ID);
        String query = executeSQLHelper.createUpdateQuery(TABLE_NAME, updateFields, conditionFields);
        return executeSQLHelper.executeUpdate(
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
        String query = "UPDATE Product SET isDeleted=1 WHERE productId=?";
        return executeSQLHelper.executeUpdate(query, productId);
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