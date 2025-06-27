package com.atbm.dao.location.district;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.District;

import java.util.List;

public interface DistrictDao {
    String TABLE_NAME = "district";
    String DISTRICT_ID = "id";
    String DISTRICT_NAME = "name";
    String PROVINCE_ID = "provinceId";
    String DISTRICT_CODE = "code";
    SQLTransactionStep<Long> insert(District district);
    List<District> getDistrictsByProvinceId(long provinceId);
    District getDistrictById(long districtId);
    boolean existsDistrict(long districtId, long provinceId);
    String getDistrictNameById(long districtId);

}
