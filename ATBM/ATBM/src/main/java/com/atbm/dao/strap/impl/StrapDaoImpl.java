package com.atbm.dao.strap.impl;

import com.atbm.dao.strap.StrapDao;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Strap;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StrapDaoImpl implements StrapDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public StrapDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }


    @Override
    public Strap getStrapById(long strapId) {
        String query = "SELECT * FROM Strap WHERE strapId = ?";
        try {
            return createStrap(executeSQLHelper.executeQuery(query, strapId));
        } catch (SQLException e) {
            LogUtils.debug(StrapDaoImpl.class, e.getMessage());
            throw new RuntimeException("Lấy Strap lỗi");
        }
    }

    @Override
    public List<Strap> getStraps() {
        String query = "SELECT * FROM Strap";
        List<Strap> straps = new ArrayList<>();

        try (ResultSet rs = executeSQLHelper.executeQuery(query)) {
            while (rs.next()) {
                Strap strap = new Strap();
                strap.setStrapId(rs.getLong("strapId"));
                strap.setColor(rs.getString("color"));
                strap.setMaterial(rs.getString("material"));
                strap.setLength(rs.getDouble("length"));
                straps.add(strap);
            }
        } catch (SQLException e) {
            LogUtils.debug(getClass(), e.getMessage());
            throw new RuntimeException("Lỗi khi lấy danh sách dây đeo");
        }

        return straps;
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