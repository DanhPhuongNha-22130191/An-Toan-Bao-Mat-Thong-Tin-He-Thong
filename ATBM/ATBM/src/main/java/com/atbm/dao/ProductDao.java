package com.atbm.dao;

import com.atbm.models.Brand;
import com.atbm.models.Strap;
import com.atbm.models.Product;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDao implements IDao<Product, Long> {

    @Override
    public boolean insert(Product product) {
        String query = "INSERT INTO Product (name, price, description, stock, image, haveTrending, size, waterResistance, brandId, strapId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return ExecuteSQLUtil.executeUpdate(query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                new java.io.ByteArrayInputStream(product.getImage()),
                product.isHaveTrending(),
                product.getSize(),
                product.isWaterResistance(),
                product.getBrandId(),
                product.getStrapId());
    }

    @Override
    public boolean update(Product product) {
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, image = ?, brandId = ?, status = ? " +
                "WHERE productId = ?";
        return ExecuteSQLUtil.executeUpdate(query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                new java.io.ByteArrayInputStream(product.getImage()),
                product.getBrandId(),
                product.getStatus(),
                product.getProductId());
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Product WHERE productId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    @Override
    public Product getById(Long id) {
        String query = "SELECT * FROM Product WHERE productId = ? AND isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id);
        try {
            if (rs != null && rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProductsByBrandId(long brandId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE brandId = ? AND isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, brandId);
        try {
            while (rs != null && rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getProductsByStrapId(long strapId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE strapId = ? AND isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, strapId);
        try {
            while (rs != null && rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getTrendingProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE haveTrending = 1 AND isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> filterProducts(List<Long> brandIds, List<Long> strapIds, Double minPrice, Double maxPrice) {
        List<Product> products = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Product WHERE isDeleted = 0 ");

        List<Object> params = new ArrayList<>();

        if (brandIds != null && !brandIds.isEmpty()) {
            query.append("AND brandId IN (");
            query.append(brandIds.stream().map(id -> "?").collect(Collectors.joining(",")));
            query.append(") ");
            params.addAll(brandIds);
        }

        if (strapIds != null && !strapIds.isEmpty()) {
            query.append("AND strapId IN (");
            query.append(strapIds.stream().map(id -> "?").collect(Collectors.joining(",")));
            query.append(") ");
            params.addAll(strapIds);
        }

        if (minPrice != null) {
            query.append("AND price >= ? ");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            query.append("AND price <= ? ");
            params.add(maxPrice);
        }

        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query.toString(), params.toArray());

        try {
            while (rs != null && rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Double getMinPrice() {
        String query = "SELECT MIN(price) AS minPrice FROM Product WHERE isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getDouble("minPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Double getMaxPrice() {
        String query = "SELECT MAX(price) AS maxPrice FROM Product WHERE isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getDouble("maxPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        byte[] imageBytes = rs.getBytes("image");
        return new Product(
                rs.getLong("productId"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock"),
                imageBytes,
                rs.getBoolean("haveTrending"),
                rs.getDouble("size"),
                rs.getBoolean("waterResistance"),
                rs.getLong("brandId"),
                rs.getLong("strapId"),
                rs.getString("status"),
                rs.getBoolean("isDeleted")
        );
    }

    public List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        Set<Long> brandIds = new HashSet<>();

        String query = "SELECT DISTINCT name, brandId FROM Brand";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                long id = rs.getLong("brandId");
                if (!brandIds.contains(id)) {
                    brands.add(new Brand(id, rs.getString("name")));
                    brandIds.add(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }


    public List<Strap> getStraps() {
        List<Strap> straps = new ArrayList<>();
        Set<String> seenMaterials = new HashSet<>();

        String query = "SELECT * FROM Strap";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                String material = rs.getString("material");
                if (!seenMaterials.contains(material)) {
                    straps.add(new Strap(
                            rs.getLong("strapId"),
                            rs.getString("color"),
                            material,
                            rs.getDouble("length")
                    ));
                    seenMaterials.add(material);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return straps;
    }


    public int getTotalProductStock() {
        String query = "SELECT SUM(stock) AS totalStock FROM Product WHERE isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getInt("totalStock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countLowStockProducts() {
        String query = "SELECT COUNT(*) AS lowStockCount FROM Product WHERE stock <= 2 AND isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getInt("lowStockCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalBrandCount() {
        String query = "SELECT COUNT(*) AS brandCount FROM Brand";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getInt("brandCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getImageByProductId(long id) {
        String query = "SELECT image FROM Product WHERE productId = ? AND isDeleted = 0";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id);
        try {
            if (rs != null && rs.next()) {
                return rs.getString("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(long productId) {
        String query = "UPDATE Product SET isDeleted = 1 WHERE productId = ?";
        return ExecuteSQLUtil.executeUpdate(query, productId);
    }
}
