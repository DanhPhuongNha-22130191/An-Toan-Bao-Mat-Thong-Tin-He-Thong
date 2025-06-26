package com.atbm.dao.location.province;

import com.atbm.models.entity.Province;

import java.util.List;

public interface ProvinceDao {
    String TABLE_NAME = "province";
    String PROVINCE_ID = "id";
    String PROVINCE_NAME = "name";
    String PROVINCE_CODE = "code";
    void insert(String provinceName, String provinceCode);
    Province getProvinceById(String provinceId);
    List<Province> getProvinces();
    String getProvinceNameById(long provinceId);
}
