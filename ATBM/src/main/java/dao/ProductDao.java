package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dto.Brand;
import dto.Strap;
import models.Product;
import utils.ExecuteSQLUtil;

public class ProductDao implements IDao<Product, Long> {

	// Thêm sản phẩm mới
	@Override
	public boolean insert(Product product) {
		String query = "INSERT INTO Product (name, price, description, stock, image, haveTrending, size, waterResistance, batteryLife) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return ExecuteSQLUtil.executeUpdate(query, product.getName(), product.getPrice(), product.getDescription(),
				product.getStock(), product.getImage(), product.isHaveTrending(), product.getSize(),
				product.isWaterResistance());
	}

	// Cập nhật thông tin sản phẩm
	public boolean update(Product product) {
		String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, image = ?, haveTrending = ?, size = ?, waterResistance = ?, batteryLife = ? WHERE productId = ?";
		return ExecuteSQLUtil.executeUpdate(query, product.getName(), product.getPrice(), product.getDescription(),
				product.getStock(), product.getImage(), product.isHaveTrending(), product.getSize(),
				product.isWaterResistance(), product.getProductId());
	}

	// Xóa sản phẩm theo ID
	@Override
	public boolean delete(Long id) {
		String query = "DELETE FROM Product WHERE productId = ?";
		return ExecuteSQLUtil.executeUpdate(query, id);
	}

	// Lấy sản phẩm theo ID
	@Override
	public Product getById(Long id) {
		String query = "SELECT * FROM Product WHERE productId = ?";
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query, id);
		try {
			if (rs != null && rs.next()) {
				return new Product(rs.getLong("productId"), rs.getString("name"), rs.getDouble("price"),
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
		List<Product> products = new ArrayList<>();
		String query = "SELECT * FROM Product";
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				products.add(new Product(rs.getLong("productId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
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
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				trendingProducts.add(new Product(rs.getLong("productId"), rs.getString("name"), rs.getDouble("price"),
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
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				brands.add(new Brand(rs.getLong("productId"), rs.getLong("brandId"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return brands;
	}

	// Lấy ds sản phẩm bằng brandId
	public List<Product> getProductsByBrand(String brandId) {
		List<Product> products = new ArrayList<>();
		String query = "SELECT p.* FROM Product p " + "JOIN Brand b ON p.productId = b.productId "
				+ "WHERE b.brandId = ?";
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query, brandId);
		try {
			while (rs != null && rs.next()) {
				products.add(new Product(rs.getLong("productId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	// Lấy ds sản phẩm bằng id dây đồng hồ
	public List<Product> getProductByStrap(String strapId) {
		List<Product> products = new ArrayList<>();
		String query = "SELECT p.* FROM Product p " + "JOIN Strap s ON p.productId = s.productId "
				+ "WHERE s.strapId = ?";
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query, strapId);
		try {
			while (rs != null && rs.next()) {
				products.add(new Product(rs.getLong("productId"), rs.getString("name"), rs.getDouble("price"),
						rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"), rs.getDouble("size"), rs.getBoolean("waterResistance"),
						rs.getDouble("batteryLife")));
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
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query);
		try {
			while (rs != null && rs.next()) {
				straps.add(new Strap(rs.getLong("productId"), rs.getLong("strapId"), rs.getString("color"),
						rs.getString("material"), rs.getDouble("length")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return straps;
	}
	

	public List<Product> getProductsByBrandId(int brandId) {
	    List<Product> products = new ArrayList<>();
	    String query = "SELECT p.* FROM Product p " +
	                   "JOIN Brand b ON p.productId = b.productId " +
	                   "WHERE b.brandId = ?";
	    ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query, brandId);
	    try {
	        while (rs != null && rs.next()) {
	            products.add(new Product(
	                rs.getLong("productId"),
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
	    return products;
	}

	public static void main(String[] args) {
		ProductDao dao = new ProductDao();
		System.out.println(dao.getProductsByBrandId(1));
	}

}