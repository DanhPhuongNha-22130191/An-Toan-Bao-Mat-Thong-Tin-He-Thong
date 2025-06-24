package com.atbm.dao.shippingInfo;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.ShippingInfo;

public interface ShippingInfoDao {
    String TABLE_NAME = "ShippingInfo";
    String SHIPPING_INFO_ID = "shippingInfoId";
    String RECEIVER_NAME = "receiverName";
    String PHONE_NUMBER = "phoneNumber";
    String ADDRESS_LINE = "addressLine";
    String DISTRICT = "district";
    String PROVINCE = "province";
    String WARD = "ward";
    String SHIPPING_METHOD = "shippingMethod";
    String SHIPPING_FEE = "shippingFee";
    String NOTE = "note";
    String DISTRICT_ID = "districtId";
    String PROVINCE_ID = "provinceId";
    String WARD_ID = "wardId";

    SQLTransactionStep<Long> insert(ShippingInfo shippingInfo);

    ShippingInfo getShippingInfoById(long shippingInfoId);
}
