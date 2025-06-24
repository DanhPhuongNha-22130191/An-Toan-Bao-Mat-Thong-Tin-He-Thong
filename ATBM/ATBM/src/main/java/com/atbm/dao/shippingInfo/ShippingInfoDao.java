package com.atbm.dao.shippingInfo;

import com.atbm.database.SQLTransactionStep;
import com.atbm.models.entity.ShippingInfo;

public interface ShippingInfoDao {
    SQLTransactionStep<Long> insert(ShippingInfo shippingInfo);
    ShippingInfo getShippingInfoById(long shippingInfoId);
}
