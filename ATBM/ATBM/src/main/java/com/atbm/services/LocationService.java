package com.atbm.services;

import com.atbm.dao.location.district.DistrictDao;
import com.atbm.dao.location.province.ProvinceDao;
import com.atbm.dao.location.ward.WardDao;
import com.atbm.models.entity.District;
import com.atbm.models.entity.Province;
import com.atbm.models.entity.Ward;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class LocationService {
    private final ProvinceDao provinceDao;
    private final DistrictDao districtDao;
    private final WardDao wardDao;

    @Inject
    public LocationService(ProvinceDao provinceDao, DistrictDao districtDao, WardDao wardDao) {
        this.provinceDao = provinceDao;
        this.districtDao = districtDao;
        this.wardDao = wardDao;
    }

    public List<Province> getProvinces() {
        return provinceDao.getProvinces();
    }

    public List<District> getDistricts(Long provinceId) {
        return districtDao.getDistrictsByProvinceId(provinceId);
    }

    public List<Ward> getWards(Long districtId) {
        return wardDao.getWardsByDistrictId(districtId);
    }

    public boolean validationLocation(long provinceId, long districtId, long wardId) {
        return districtDao.existsDistrict(districtId, provinceId) && wardDao.existsWard(wardId, districtId);
    }

    public String getWardName(long wardId) {
        return wardDao.getWardNameById(wardId);
    }

    public String getDistrictName(long districtId) {
        return districtDao.getDistrictNameById(districtId);
    }

    public String getProvinceName(long provinceId) {
        return provinceDao.getProvinceNameById(provinceId);
    }

    /**
     * Triển khai sau
     */
    public double calculateShippingFee(long provinceId, long districtId, long wardId) {
        return 0;
    }
}
