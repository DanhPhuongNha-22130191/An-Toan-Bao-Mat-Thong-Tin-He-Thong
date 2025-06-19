package com.atbm.dao.orderSecurity.impl;

import com.atbm.dao.orderSecurity.OrderSecurityDao;
import com.atbm.models.entity.OrderSecurity;

public class OrderSecurityDaoImpl implements OrderSecurityDao {
    @Override
    public OrderSecurity getOrderSecurityById(long orderSecurityId) {
        return null;
    }

    @Override
    public boolean insert(OrderSecurity orderSecurity) {
        return false;
    }

    @Override
    public boolean update(OrderSecurity orderSecurity) {
        return false;
    }

    @Override
    public boolean updateSignature(long orderSecurityId, String signature) {
        return false;
    }
}
