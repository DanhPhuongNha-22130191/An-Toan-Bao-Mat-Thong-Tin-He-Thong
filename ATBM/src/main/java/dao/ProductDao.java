package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Product;
import utils.ExecuteSQLUtil;

public class ProductDao implements IDao<Product, Long> {

	@Override
	public boolean insert(Product entity) {
		// TODO Auto-generated method stub
		return false;
	}

	// Lấy sản phẩm bằng ID
	@Override
	public Product getById(Long id) {
		String query = "SELECT * FROM Product WHERE productId = ?";
		ResultSet rs = ExecuteSQLUtil.ExcuteQuery(query, id);
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

	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}