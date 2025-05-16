package com.atbm.services;


import com.atbm.dao.CategoryDao;
import models.Category;

import java.util.List;

public class CategoryService implements services.IService<Category, Long> {
    private CategoryDao categoryDao = new CategoryDao();

    @Override
    public boolean insert(Category category) {
        return categoryDao.insert(category);
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryDao.getById(categoryId);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public boolean delete(Long categoryId) {
        return categoryDao.delete(categoryId);
    }

    @Override
    public boolean update(Category category) {
        return categoryDao.update(category);
    }
}
