package com.atbm.dao.location.province.impl;

import com.atbm.dao.location.province.ProvinceDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Province;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ProvinceDaoImpl implements ProvinceDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public ProvinceDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }

    @Override
    public SQLTransactionStep<Long> insert(Province province) {
        if (existsProvince(province.getCode())) {
            return connection -> null;
        }
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(PROVINCE_NAME, PROVINCE_CODE));
        return executeSQLHelper.buildInsertStepReturningId(query, province.getName(), province.getCode());
    }

    @Override
    public Province getProvinceById(String provinceId) {
        String query = "SELECT * FROM Province WHERE id = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, provinceId)) {
            return createProvince(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Province> getProvinces() {
        String query = "SELECT * FROM Province";
        try (ResultSet rs = executeSQLHelper.executeQuery(query)) {
            List<Province> result = new java.util.ArrayList<>();
            while (rs.next()) {
                result.add(createProvince(rs));
            }
            return result;
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getProvinceNameById(long provinceId) {
        String query = "SELECT NAME FROM ward WHERE id = ? ";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, provinceId)) {
            if (rs.next())
                return rs.getString(PROVINCE_NAME);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsProvince(int provinceCode) {
        String query = "SELECT * FROM Province WHERE code = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, provinceCode)) {
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Province createProvince(ResultSet resultSet) throws Exception {
        return new Province(
                resultSet.getLong(PROVINCE_ID),
                resultSet.getInt(PROVINCE_CODE),
                resultSet.getString(PROVINCE_NAME));
    }

}
