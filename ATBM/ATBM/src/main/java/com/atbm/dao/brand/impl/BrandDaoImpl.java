package com.atbm.dao.brand.impl;

import com.atbm.dao.brand.BrandDao;
import com.atbm.models.entity.Brand;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class BrandDaoImpl implements BrandDao {
    @Inject
    private ExecuteSQLHelper executeSQLHelper;
    private static final String BRAND_ID = "brandId";
    private static final String NAME = "name";

    @Override
    public Brand getBrandById(long brandId) {
        String query = "SELECT * FROM Brand WHERE brandId = ?";
        try {
            return createBrand(executeSQLHelper.executeQuery(query, brandId));
        } catch (SQLException e) {
            LogUtils.debug(BrandDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy brand lỗi");
        }
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