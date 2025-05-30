package com.atbm.services;

import com.atbm.dao.OrderHashDao;
import com.atbm.models.Order;
import com.atbm.models.OrderHash;
import com.atbm.utils.HashUtil;

public class OrderSecurityService {
    private OrderHashDao orderHashDao = new OrderHashDao();

    public void saveOrderHash(Order order) {
        String hash = HashUtil.generateOrderHash(order);
        OrderHash orderHash = new OrderHash(order.getOrderId(), hash);
        orderHashDao.insert(orderHash);
    }

    public boolean isOrderTampered(Order order) {
        OrderHash storedHash = orderHashDao.getByOrderId(order.getOrderId());
        if (storedHash == null) return true;
        String currentHash = HashUtil.generateOrderHash(order);
        return !currentHash.equals(storedHash.getHash());
    }
}