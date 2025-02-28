package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Product;
import utils.ExecuteSQLUtil;

public class ProductDao implements IDao<Product, Long> {

	// Thêm sản phẩm mới
	@Override
	public boolean insert(Product product) {
		String query = "INSERT INTO Product (categoryId, name, price, description, stock, image, haveTrending) VALUES (?, ?, ?, ?, ?, ?, ?)";
		return ExecuteSQLUtil.executeUpdate(query, product.getCategoryId(), product.getName(), product.getPrice(),
				product.getDescription(), product.getStock(), product.getImage(), product.isHaveTrending());
	}

	// Cập nhật thông tin sản phẩm
	public boolean update(Product product) {
		String query = "UPDATE Product SET categoryId = ?, name = ?, price = ?, description = ?, stock = ?, image = ?, haveTrending = ? WHERE productId = ?";
		return ExecuteSQLUtil.executeUpdate(query, product.getCategoryId(), product.getName(), product.getPrice(),
				product.getDescription(), product.getStock(), product.getImage(), product.isHaveTrending(),
				product.getProductId());
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
		ResultSet rs = ExecuteSQLUtil.executeQuery(query, id);
		try {
			if (rs != null && rs.next()) {
				return new Product(rs.getLong("productId"), rs.getLong("categoryId"), rs.getString("name"),
						rs.getDouble("price"), rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending"));
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
		ResultSet rs = ExecuteSQLUtil.executeQuery(query);
		try {
			while (rs != null && rs.next()) {
				products.add(new Product(rs.getLong("productId"), rs.getLong("categoryId"), rs.getString("name"),
						rs.getDouble("price"), rs.getString("description"), rs.getInt("stock"), rs.getString("image"),
						rs.getBoolean("haveTrending")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
}
