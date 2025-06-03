package com.atbm.dao;

import com.atbm.models.Brand;
import com.atbm.models.Strap;
import com.atbm.models.Product;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                product.getImage(),
                product.isHaveTrending(),
                product.getSize(),
                product.isWaterResistance(),
                product.getBrandId(),
                product.getStrapId());
    }

    @Override
    public boolean update(Product product) {
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, image = ?, haveTrending = ?, size = ?, waterResistance = ?, brandId = ?, strapId = ? " +
                "WHERE productId = ?";
        return ExecuteSQLUtil.executeUpdate(query,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImage(),
                product.isHaveTrending(),
                product.getSize(),
                product.isWaterResistance(),
                product.getBrandId(),
                product.getStrapId(),
                product.getProductId());
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Product WHERE productId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    @Override
    public Product getById(Long id) {
        String query = "SELECT * FROM Product WHERE productId = ?";
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
        String query = "SELECT * FROM Product";
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

    // Lấy danh sách sản phẩm theo brandId
    public List<Product> getProductsByBrandId(long brandId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE brandId = ?";
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

    // Lấy danh sách sản phẩm theo strapId
    public List<Product> getProductsByStrapId(long strapId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE strapId = ?";
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

    // Lấy danh sách sản phẩm trending
    public List<Product> getTrendingProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE haveTrending = 1";
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

    // Phương thức lọc sản phẩm theo brandId, strapId, giá min max
    public List<Product> filterProducts(List<Long> brandIds, List<Long> strapIds, Double minPrice, Double maxPrice) {
        List<Product> products = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Product WHERE 1=1 ");

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

    // Hàm lấy giá min
    public Double getMinPrice() {
        String query = "SELECT MIN(price) AS minPrice FROM Product";
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

    // Hàm lấy giá max
    public Double getMaxPrice() {
        String query = "SELECT MAX(price) AS maxPrice FROM Product";
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

    // Hàm ánh xạ ResultSet thành Product
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("productId"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("description"),
                rs.getInt("stock"),
                rs.getString("image"),
                rs.getBoolean("haveTrending"),
                rs.getDouble("size"),
                rs.getBoolean("waterResistance"),
                rs.getLong("brandId"),
                rs.getLong("strapId")
        );
    }

    // Lấy danh sách nhãn hàng (Brand)
    public List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM Brand";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                // Giả sử Brand có constructor Brand(long brandId, String name)
                brands.add(new Brand(
                        rs.getLong("brandId"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    // Lấy danh sách dây đồng hồ (Strap)
    public List<Strap> getStraps() {
        List<Strap> straps = new ArrayList<>();
        String query = "SELECT * FROM Strap";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                // Giả sử Strap có constructor Strap(long strapId, String color, String material, double length)
                straps.add(new Strap(
                        rs.getLong("strapId"),
                        rs.getString("color"),
                        rs.getString("material"),
                        rs.getDouble("length")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return straps;
    }

}
