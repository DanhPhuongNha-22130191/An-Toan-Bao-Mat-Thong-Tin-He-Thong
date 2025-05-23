package com.atbm.dao;

import com.atbm.dto.Brand;
import com.atbm.dto.Strap;
import com.atbm.models.Product;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDao implements IDao<Product, Long> {

    // Thêm sản phẩm mới
    @Override
    public boolean insert(Product product) {
        String query = "INSERT INTO Product (name, price, description, stock, image, haveTrending, size, waterResistance) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return ExecuteSQLUtil.executeUpdate(query, product.getName(), product.getPrice(), product.getDescription(),
                product.getStock(), product.getImage(), product.isHaveTrending(), product.getSize(),
                product.isWaterResistance());
    }

    // Cập nhật thông tin sản phẩm
    public boolean update(Product product) {
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, image = ?, haveTrending = ?, size = ?, waterResistance = ? WHERE ProductId = ?";
        return ExecuteSQLUtil.executeUpdate(query, product.getName(), product.getPrice(), product.getDescription(),
                product.getStock(), product.getImage(), product.isHaveTrending(), product.getSize(),
                product.isWaterResistance(), product.getProductId());
    }

    // Xóa sản phẩm theo ID
    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Product WHERE ProductId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    // Lấy sản phẩm theo ID
    @Override
    public Product getById(Long id) {
        String query = "SELECT * FROM Product WHERE ProductId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id);
        try {
            if (rs != null && rs.next()) {
                return new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả sản phẩm
    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                products.add(new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Lấy danh sách sản phẩm trending
    public List<Product> getTrendingProducts() {
        List<Product> trendingProducts = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE haveTrending = 1";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                trendingProducts.add(new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trendingProducts;
    }

    // Lấy danh sách nhãn hàng
    public List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM Brand";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                brands.add(new Brand(rs.getLong("ProductId"), rs.getLong("brandId"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    // Lấy sản phẩm theo brandId
    public List<Product> getProductsByBrand(String brandId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.* FROM Product p JOIN Brand b ON p.ProductId = b.ProductId WHERE b.brandId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, brandId);
        try {
            while (rs != null && rs.next()) {
                products.add(new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Lấy sản phẩm theo strapId
    public List<Product> getProductByStrap(String strapId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.* FROM Product p JOIN Strap s ON p.ProductId = s.ProductId WHERE s.strapId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, strapId);
        try {
            while (rs != null && rs.next()) {
                products.add(new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Lấy danh sách dây đồng hồ
    public List<Strap> getStraps() {
        List<Strap> straps = new ArrayList<>();
        String query = "SELECT * FROM Strap";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                straps.add(new Strap(
                        rs.getLong("ProductId"),
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

    // Lấy sản phẩm theo brandId (int)
    public List<Product> getProductsByBrandId(int brandId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.* FROM Product p JOIN Brand b ON p.ProductId = b.ProductId WHERE b.brandId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, brandId);
        try {
            while (rs != null && rs.next()) {
                products.add(new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> filterProducts(List<Integer> brandIds, List<Integer> strapIds, Double minPrice, Double maxPrice) {
        List<Product> products = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT DISTINCT p.* FROM Product p " +
                        "LEFT JOIN Brand b ON p.ProductId = b.ProductId " +
                        "LEFT JOIN Strap s ON p.ProductId = s.ProductId " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (brandIds != null && !brandIds.isEmpty()) {
            query.append("AND b.brandId IN (");
            query.append(brandIds.stream().map(id -> "?").collect(Collectors.joining(",")));
            query.append(") ");
            params.addAll(brandIds);
        }

        if (strapIds != null && !strapIds.isEmpty()) {
            query.append("AND s.strapId IN (");
            query.append(strapIds.stream().map(id -> "?").collect(Collectors.joining(",")));
            query.append(") ");
            params.addAll(strapIds);
        }

        if (minPrice != null) {
            query.append("AND p.price >= ? ");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            query.append("AND p.price <= ? ");
            params.add(maxPrice);
        }

        System.out.println("Generated SQL: " + query.toString());

        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query.toString(), params.toArray());

        try {
            while (rs != null && rs.next()) {
                products.add(new Product(
                        rs.getLong("ProductId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("image"),
                        rs.getBoolean("haveTrending"),
                        rs.getDouble("size"),
                        rs.getBoolean("waterResistance")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Double getMinPrice() {
        String query = "SELECT MIN(price) AS min_price FROM Product";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getDouble("min_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


    public Double getMaxPrice() {
        String query = "SELECT MAX(price) AS max_price FROM Product";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            if (rs != null && rs.next()) {
                return rs.getDouble("max_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1000.0;
    }


}
