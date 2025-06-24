package com.atbm.dao.shippingInfo.impl;

import com.atbm.dao.shippingInfo.ShippingInfoDao;
import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.ShippingInfo;
import com.atbm.helper.ExecuteSQLHelper;
import com.atbm.utils.LogUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.util.List;

@ApplicationScoped
public class ShippingInfoDaoImpl implements ShippingInfoDao {
    @Inject
    private ExecuteSQLHelper executeSQLHelper;
    private static final String TABLE_NAME = "ShippingInfo";
    private static final String SHIPPING_INFO_ID = "shippingInfoId";
    private static final String RECEIVER_NAME = "receiverName";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String ADDRESS_LINE = "addressLine";
    private static final String DISTRICT = "district";
    private static final String PROVINCE = "province";
    private static final String WARD = "ward";
    private static final String SHIPPING_METHOD = "shippingMethod";
    private static final String SHIPPING_FEE = "shippingFee";
    private static final String NOTE = "note";
    private static final String DISTRICT_ID = "districtId";
    private static final String PROVINCE_ID = "provinceId";
    private static final String WARD_ID = "wardId";

    @Override
    public SQLTransactionStep<Long> insert(ShippingInfo shippingInfo) {
        String query = executeSQLHelper.createInsertQuery(TABLE_NAME, List.of(
                RECEIVER_NAME,
                PHONE_NUMBER,
                ADDRESS_LINE,
                DISTRICT,
                PROVINCE,
                WARD,
                NOTE,
                SHIPPING_METHOD,
                SHIPPING_FEE,
                PROVINCE_ID,
                DISTRICT_ID,
                WARD_ID
        ));
        return executeSQLHelper.buildInsertStepReturningId(query,
                shippingInfo.getRecipientName(),
                shippingInfo.getPhoneNumber(),
                shippingInfo.getAddressLine(),
                shippingInfo.getDistrict(),
                shippingInfo.getProvince(),
                shippingInfo.getWard(),
                shippingInfo.getNote(),
                shippingInfo.getShippingMethod(),
                shippingInfo.getShippingFee(),
                shippingInfo.getProvinceId(),
                shippingInfo.getDistrictId(),
                shippingInfo.getWardId()
        );
    }

    @Override
    public ShippingInfo getShippingInfoById(long shippingInfoId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE shippingInfoId = ?";
        try {
            var resultSet = executeSQLHelper.executeQuery(query, shippingInfoId);
            if (resultSet.next()) {
                return createShippingInfo(resultSet);
            }
        } catch (Exception e) {
            LogUtils.debug(ShippingInfoDaoImpl.class, e.getMessage());
        }
        return null;
    }

    private ShippingInfo createShippingInfo(ResultSet resultSet) throws Exception {
        return new ShippingInfo(
                resultSet.getLong(SHIPPING_INFO_ID),
                resultSet.getString(RECEIVER_NAME),
                resultSet.getString(PHONE_NUMBER),
                resultSet.getString(ADDRESS_LINE),
                resultSet.getString(DISTRICT),
                resultSet.getString(PROVINCE),
                resultSet.getString(WARD),
                resultSet.getString(NOTE),
                resultSet.getString(SHIPPING_METHOD),
                resultSet.getDouble(SHIPPING_FEE),
                resultSet.getLong(PROVINCE_ID),
                resultSet.getLong(DISTRICT_ID),
                resultSet.getLong(WARD_ID)
        );
    }
}
