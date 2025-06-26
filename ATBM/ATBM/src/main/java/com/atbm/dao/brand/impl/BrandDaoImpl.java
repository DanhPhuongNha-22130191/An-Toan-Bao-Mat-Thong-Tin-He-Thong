package com.atbm.dao.brand.impl;

import com.atbm.dao.brand.BrandDao;
import com.atbm.models.entity.Brand;
import com.atbm.utils.ExecuteSQLUtils;
import com.atbm.utils.LogUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDaoImpl implements BrandDao {
    private static final String BRAND_ID = "brandId";
    private static final String NAME = "name";

    @Override
    public Brand getBrandById(long brandId) {
        String query = "SELECT * FROM Brand WHERE brandId = ?";
        try {
            return createBrand(ExecuteSQLUtils.executeQuery(query, brandId));
        } catch (SQLException e) {
            LogUtils.debug(BrandDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy brand lỗi");
        }
    }

    @Override
    public List<Brand> getBrands() {
        String query = "SELECT * FROM Brand ";
        List<Brand> brands = new ArrayList<>();
        try (ResultSet rs = ExecuteSQLUtils.executeQuery(query)) {
            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getLong(BRAND_ID),
                        rs.getString(NAME)
                );
                brands.add(brand);
            }
        } catch (SQLException e) {
            LogUtils.debug(BrandDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy danh sách thương hiệu lỗi");
        }
        return brands;
    }


    private Brand createBrand(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Brand(
                    resultSet.getLong(BRAND_ID),
                    resultSet.getString(NAME)
            );
        }
        return null;
    }
}