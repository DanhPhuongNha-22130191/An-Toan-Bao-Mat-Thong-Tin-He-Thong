package com.atbm.dao.product.impl;

import com.atbm.dao.product.ProductDao;
import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Product;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductDaoImpl implements ProductDao {
    private final String TABLE_NAME = "Product";
    private final String PRODUCT_ID = "productId";
    private final String NAME = "name";
    private final String PRICE = "price";
    private final String DESCRIPTION = "description";
    private final String STOCK = "stock";
    private final String IMAGE = "image";
    private final String TRENDING = "isTrending";
    private final String SIZE = "size";
    private final String BRAND_ID = "brandId";
    private final String STRAP_ID = "strapId";
    private final String DELETED = "isDeleted";
    private final String WATER_RESISTANCE = "waterResistance";

    private final ExecuteSQLHelper sqlHelper;

<<<<<<< nha-refactor
    @Inject
=======
>>>>>>> refactor
    public ProductDaoImpl(ExecuteSQLHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public Product getProductById(long productId) {
        String query = "SELECT * FROM Product WHERE productId = ?";
        try (ResultSet rs = sqlHelper.executeQuery(query, productId)) {
            if (rs.next()) {
                return createProduct(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lấy Product lỗi", e);
        }
    }

    @Override
    public List<Product> getProducts() {
        String query = "SELECT * FROM Product WHERE isDeleted = 0";
        List<Product> products = new ArrayList<>();
        try (ResultSet rs = sqlHelper.executeQuery(query)) {
            while (rs.next()) {
                Product product = createProduct(rs);
                if (product != null) {
                    products.add(product);
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Lấy Product lỗi", e);
        }
    }

    @Override
    public boolean insert(Product product) {
        List<String> fieldNames = List.of(
                NAME, PRICE, DESCRIPTION, STOCK, IMAGE,
                TRENDING, SIZE, BRAND_ID, STRAP_ID, DELETED, WATER_RESISTANCE
        );
        String query = sqlHelper.createInsertQuery(TABLE_NAME, fieldNames);
        return sqlHelper.executeUpdate(
                query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImage(),
                product.isTrending(),
                product.getSize(),
                product.getBrandId(),
                product.getStrapId(),
                product.isDeleted(),
                product.isWaterResistance()
        );
    }

    @Override
    public boolean update(Product product) {
        List<String> updateFields = List.of(
                NAME, PRICE, DESCRIPTION, STOCK, IMAGE,
                TRENDING, SIZE, BRAND_ID, STRAP_ID, DELETED, WATER_RESISTANCE
        );
        List<String> conditionFields = List.of(PRODUCT_ID);
        String query = sqlHelper.createUpdateQuery(TABLE_NAME, updateFields, conditionFields);
        return sqlHelper.executeUpdate(
                query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImage(),
                product.isTrending(),
                product.getSize(),
                product.getBrandId(),
                product.getStrapId(),
                product.isDeleted(),
                product.isWaterResistance(),
                product.getProductId()
        );
    }

    @Override
    public boolean delete(long productId) {
        String query = "UPDATE Product SET isDeleted=1 WHERE productId=?";
        return sqlHelper.executeUpdate(query, productId);
    }

    private Product createProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong(PRODUCT_ID),
                rs.getString(NAME),
                rs.getDouble(PRICE),
                rs.getString(DESCRIPTION),
                rs.getInt(STOCK),
                rs.getBytes(IMAGE),
                rs.getBoolean(TRENDING),
                rs.getDouble(SIZE),
                rs.getBoolean(WATER_RESISTANCE),
                rs.getLong(BRAND_ID),
                rs.getLong(STRAP_ID)
        );
    }

    public List<Brand> getBrands() {
        String query = "SELECT * FROM Brand ";
        List<Brand> brands = new ArrayList<>();
        try (ResultSet rs = sqlHelper.executeQuery(query)) {
            while (rs.next()) {
                brands.add(new Brand(
                        rs.getLong("brandId"),
                        rs.getString("name")
                ));
            }
            return brands;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy danh sách thương hiệu", e);
        }
    }
}
