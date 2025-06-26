package com.atbm.services;

import com.atbm.dao.location.district.DistrictDao;
import com.atbm.dao.location.province.ProvinceDao;
import com.atbm.dao.location.ward.WardDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.models.entity.District;
import com.atbm.models.entity.Province;
import com.atbm.models.entity.Ward;
import com.atbm.utils.HttpClientUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LocationService {
    private final ProvinceDao provinceDao;
    private final DistrictDao districtDao;
    private final WardDao wardDao;
    private final ExecuteSQLHelper executeSQLHelper;

    public LocationService() {
        this.provinceDao = null;
        this.districtDao = null;
        this.wardDao = null;
        executeSQLHelper = null;
    }

    @Inject
    public LocationService(ProvinceDao provinceDao, DistrictDao districtDao, WardDao wardDao, ExecuteSQLHelper executeSQLHelper) {
        this.provinceDao = provinceDao;
        this.districtDao = districtDao;
        this.wardDao = wardDao;
        this.executeSQLHelper = executeSQLHelper;
        fetchLocation();
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

    private void fetchLocation() {
        Type listType = new TypeToken<List<Province>>() {
        }.getType();
        String url = "https://provinces.open-api.vn/api/?depth=3";
        try {
            List<Province> provinces = HttpClientUtils.sendGet(url, listType);
            List<SQLTransactionStep<?>> allSteps = new ArrayList<>();

            for (Province province : provinces) {
                SQLTransactionStep<Long> provinceStep = buildInsertProvinceStep(province);
                allSteps.add(connection -> {
                    province.setId(provinceStep.apply(connection));
                    return provinceStep;
                });

                for (District district : province.getDistricts()) {
                    SQLTransactionStep<Long> districtStep = buildInsertDistrictStep(district);
                    allSteps.add(connection -> {
                        district.setProvinceId(province.getId());
                        district.setId(districtStep.apply(connection));
                        return districtStep;
                    });

                    for (Ward ward : district.getWards()) {
                        SQLTransactionStep<Long> wardStep = buildInsertWardStep(ward);
                        allSteps.add(connection -> {
                            ward.setId(wardStep.apply(connection));
                            return wardStep;
                        });
                    }
                }
            }
            boolean success = executeSQLHelper.executeStepsInTransaction(allSteps);
            if (!success) {
                throw new RuntimeException("Import địa chỉ thất bại");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SQLTransactionStep<Long> buildInsertProvinceStep(Province province) {
        return provinceDao.insert(province);
    }

    private SQLTransactionStep<Long> buildInsertDistrictStep(District district) {
        return districtDao.insert(district);
    }

    private SQLTransactionStep<Long> buildInsertWardStep(Ward ward) {
        return wardDao.insert(ward);
    }

}