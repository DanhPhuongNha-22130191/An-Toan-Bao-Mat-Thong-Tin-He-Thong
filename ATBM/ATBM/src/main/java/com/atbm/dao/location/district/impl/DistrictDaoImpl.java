package com.atbm.dao.location.district.impl;

import com.atbm.dao.location.district.DistrictDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.District;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.util.List;

@ApplicationScoped
public class DistrictDaoImpl implements DistrictDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public DistrictDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }


    @Override
    public SQLTransactionStep<Long> insert(District district) {
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(DISTRICT_NAME, DISTRICT_CODE, PROVINCE_ID));
        return executeSQLHelper.buildInsertStepReturningId(query, district.getName(), district.getCode(), district.getProvinceId());
    }

    @Override
    public List<District> getDistrictsByProvinceId(long provinceId) {
        String query = "SELECT * FROM district WHERE provinceId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, provinceId)) {
            List<District> result = new java.util.ArrayList<>();
            while (rs.next()) {
                result.add(createDistrict(rs));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public District getDistrictById(long districtId) {
        return null;
    }

    @Override
    public boolean existsDistrict(long districtId, long provinceId) {
        String query = "SELECT * FROM district WHERE id = ? AND provinceId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, districtId, provinceId)) {
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDistrictNameById(long districtId) {
        String query = "SELECT NAME FROM district WHERE id = ? ";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, districtId)) {
            if (rs.next())
                return rs.getString(DISTRICT_NAME);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public District createDistrict(ResultSet resultSet) throws Exception {
        return new District(resultSet.getLong(DISTRICT_ID), resultSet.getLong(PROVINCE_ID), resultSet.getString(DISTRICT_NAME), resultSet.getInt(DISTRICT_CODE));
    }
}
