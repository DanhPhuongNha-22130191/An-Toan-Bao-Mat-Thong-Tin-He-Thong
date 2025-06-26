package com.atbm.dao.location.ward;

import com.atbm.models.entity.Ward;

import java.util.List;

public interface WardDao {
    String TABLE_NAME = "ward";
    String WARD_ID = "id";
    String WARD_NAME = "name";
    String WARD_CODE = "code";
    String WARD_DISTRICT_ID = "districtId";

    void insert(Ward ward);

    Ward getWardById(long wardId);

    List<Ward> getWards();
    List<Ward> getWardsByDistrictId(long districtId);
    boolean existsWard(long wardId, long districtId);
    String getWardNameById(long wardId);
}
