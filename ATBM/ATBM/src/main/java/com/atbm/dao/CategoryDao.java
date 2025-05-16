package com.atbm.dao;

import com.atbm.utils.ExecuteSQLUtil;
import models.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements IDao<Category, Long> {

    // Thêm danh mục mới
    @Override
    public boolean insert(Category category) {
        String query = "INSERT INTO Category (name, image) VALUES (?, ?)";
        return ExecuteSQLUtil.executeUpdate(query, category.getName(), category.getImage());
    }

    // Cập nhật danh mục
    public boolean update(Category category) {
        String query = "UPDATE Category SET name = ?, image = ? WHERE categoryId = ?";
        return ExecuteSQLUtil.executeUpdate(query, category.getName(), category.getImage(), category.getCategoryId());
    }

    // Xóa danh mục theo ID
    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Category WHERE categoryId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    // Lấy danh mục theo ID
    @Override
    public Category getById(Long id) {
        String query = "SELECT * FROM Category WHERE categoryId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id);
        try {
            if (rs != null && rs.next()) {
                return new Category(
                        rs.getLong("categoryId"),
                        rs.getString("name"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả danh mục
    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Category";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        try {
            while (rs != null && rs.next()) {
                categories.add(new Category(
                        rs.getLong("categoryId"),
                        rs.getString("name"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
