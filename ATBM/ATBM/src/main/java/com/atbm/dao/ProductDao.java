package com.atbm.dao;


import com.atbm.dto.Brand;
import com.atbm.dto.Strap;
import com.atbm.models.Product;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IDao<Product, Long> {

	// Thêm sản phẩm mới
	@Override
	public boolean insert(Product Product) {
		String query = "INSERT INTO Product (name, price, description, stock, image, haveTrending, size, waterResistance, batteryLife) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return ExecuteSQLUtil.executeUpdate(query, Product.getName(), Product.getPrice(), Product.getDescription(),
				Product.getStock(), Product.getImage(), Product.isHaveTrending(), Product.getSize(),
				Product.isWaterResistance());
	}

	// Cập nhật thông tin sản phẩm
	public boolean update(Product Product) {
		String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, image = ?, haveTrending = ?, size = ?, waterResistance = ?, batteryLife = ? WHERE ProductId = ?";
		return ExecuteSQLUtil.executeUpdate(query, Product.getName(), Product.getPrice(), Product.getDescription(),
				Product.getStock(), Product.getImage(), Product.isHaveTrending(), Product.getSize(),
				Product.isWaterResistance(), Product.getProductId());
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
				return new Product(rs.getLong("ProductId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Lấy tất cả sản phẩm
	@Override
	public List<Product> getAll() {
		List<Product> Products = new ArrayList<>();
		String query = "SELECT * FROM Product";
		ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				Products.add(new Product(rs.getLong("ProductId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Products;
	}

	// Lấy danh sách sản phẩm trending
	public List<Product> getTrendingProducts() {
		List<Product> trendingProducts = new ArrayList<>();
		String query = "SELECT * FROM Product WHERE haveTrending = 1";
		ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				trendingProducts.add(new Product(rs.getLong("ProductId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trendingProducts;
	}

	// Lấy ds các nhãn hàng
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

	// Lấy ds sản phẩm bằng brandId
	public List<Product> getProductsByBrand(String brandId) {
		List<Product> Products = new ArrayList<>();
		String query = "SELECT p.* FROM Product p " + "JOIN Brand b ON p.ProductId = b.ProductId "
				+ "WHERE b.brandId = ?";
		ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, brandId);
		try {
			while (rs != null && rs.next()) {
				Products.add(new Product(rs.getLong("ProductId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Products;
	}

	// Lấy ds sản phẩm bằng id dây đồng hồ
	public List<Product> getProductByStrap(String strapId) {
		List<Product> Products = new ArrayList<>();
		String query = "SELECT p.* FROM Product p " + "JOIN Strap s ON p.ProductId = s.ProductId "
				+ "WHERE s.strapId = ?";
		ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, strapId);
		try {
			while (rs != null && rs.next()) {
				Products.add(new Product(rs.getLong("ProductId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Products;
	}

	// Lấy danh sách dây đồng hồ
	public List<Strap> getStraps() {
		List<Strap> straps = new ArrayList<>();
		String query = "SELECT * FROM Strap";
		ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				straps.add(new Strap(rs.getLong("ProductId"), rs.getLong("strapId"), rs.getString("color"),
						rs.getString("material"), rs.getDouble("length")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return straps;
	}
	

	public List<Product> getProductsByBrandId(int brandId) {
	    List<Product> Products = new ArrayList<>();
	    String query = "SELECT p.* FROM Product p " +
	                   "JOIN Brand b ON p.ProductId = b.ProductId " +
	                   "WHERE b.brandId = ?";
	    ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, brandId);
	    try {
	        while (rs != null && rs.next()) {
	            Products.add(new Product(
	                rs.getLong("ProductId"),
	                rs.getString("name"),
	                rs.getDouble("price"),
	                rs.getString("description"),
	                rs.getInt("stock"),
	                rs.getString("image"),
	                rs.getBoolean("haveTrending"),
	                rs.getDouble("size"),
	                rs.getBoolean("waterResistance"),
	                rs.getDouble("batteryLife")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return Products;
	}
	public List<Product> filterProducts(Integer brandId, Integer strapId, Double minPrice, Double maxPrice) {
		List<Product> products = new ArrayList<>();
		StringBuilder query = new StringBuilder(
				"SELECT DISTINCT p.* FROM Product p " +
						"LEFT JOIN Brand b ON p.ProductId = b.ProductId " +
						"LEFT JOIN Strap s ON p.ProductId = s.ProductId " +
						"WHERE 1=1 "
		);

		List<Object> params = new ArrayList<>();

		if (brandId != null) {
			query.append("AND b.brandId = ? ");
			params.add(brandId);
		}

		if (strapId != null) {
			query.append("AND s.strapId = ? ");
			params.add(strapId);
		}

		if (minPrice != null) {
			query.append("AND p.price >= ? ");
			params.add(minPrice);
		}

		if (maxPrice != null) {
			query.append("AND p.price <= ? ");
			params.add(maxPrice);
		}

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
						rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife") // Ensure this field is in the SELECT
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public static void main(String[] args) {
		ProductDao dao = new ProductDao();
		System.out.println(dao.getProductsByBrandId(1));
	}

}