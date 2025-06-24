package com.atbm.dao.location.ward.impl;

import com.atbm.dao.location.ward.WardDao;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.Ward;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.util.List;

@ApplicationScoped
public class WardDaoImpl implements WardDao {
    private final ExecuteSQLHelper executeSQLHelper;

    @Inject
    public WardDaoImpl(ExecuteSQLHelper executeSQLHelper) {
        this.executeSQLHelper = executeSQLHelper;
    }

    @Override
    public void insert(Ward ward) {
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(WARD_NAME, WARD_DISTRICT_ID, WARD_CODE));
        executeSQLHelper.executeUpdate(query, ward.getName(), ward.getDistrictId(), ward.getCode());
    }

    @Override
    public Ward getWardById(long wardId) {
        String query = "SELECT * FROM Ward WHERE id = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, wardId)) {
            return createWard(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ward> getWards() {
        String query = "SELECT * FROM Ward";
        try (ResultSet rs = executeSQLHelper.executeQuery(query)) {
            List<Ward> result = new java.util.ArrayList<>();
            while (rs.next()) {
                result.add(createWard(rs));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ward> getWardsByDistrictId(long districtId) {
        String query = "SELECT * FROM Ward WHERE districtId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, districtId)) {
            List<Ward> result = new java.util.ArrayList<>();
            while (rs.next()) {
                result.add(createWard(rs));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsWard(long wardId, long districtId) {
        String query = "SELECT * FROM ward WHERE id = ? AND districtId = ?";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, wardId, districtId)) {
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getWardNameById(long wardId) {
        String query = "SELECT NAME FROM ward WHERE id = ? ";
        try (ResultSet rs = executeSQLHelper.executeQuery(query, wardId)) {
            if (rs.next())
                return rs.getString(WARD_NAME);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Ward createWard(ResultSet resultSet) throws Exception {
        return new Ward(
                resultSet.getLong(WARD_ID),
                resultSet.getString(WARD_NAME),
                resultSet.getLong(WARD_DISTRICT_ID),
                resultSet.getInt(WARD_CODE)
        );
    }
}
