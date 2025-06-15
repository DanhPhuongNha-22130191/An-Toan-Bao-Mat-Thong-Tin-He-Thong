package com.atbm.dao.strap.impl;

import com.atbm.dao.strap.StrapDao;
import com.atbm.models.entity.Strap;
import com.atbm.utils.ExecuteSQLUtils;
import com.atbm.utils.LogUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StrapDaoImpl implements StrapDao {
    private static final String STRAP_ID = "strapId";
    private static final String COLOR = "color";
    private static final String MATERIAL = "material";
    private static final String LENGTH = "length";

    @Override
    public Strap getStrapById(long strapId) {
        String query = "SELECT * FROM Strap WHERE strapId = ?";
        try {
            return createStrap(ExecuteSQLUtils.ExecuteQuery(query, strapId));
        } catch (SQLException e) {
            LogUtils.debug(StrapDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Strap lỗi");
        }
    }

    private Strap createStrap(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Strap(
                    resultSet.getDouble(LENGTH),
                    resultSet.getString(MATERIAL),
                    resultSet.getString(COLOR),
                    resultSet.getLong(STRAP_ID)
            );
        }
        return null;
    }
}