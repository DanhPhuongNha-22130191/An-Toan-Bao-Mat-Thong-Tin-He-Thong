package com.atbm.dto;

import com.atbm.models.Order;

public class OrderWithStatus {
    private Order order;
    private boolean isTampered;

    public OrderWithStatus(Order order, boolean isTampered) {
        this.order = order;
        this.isTampered = isTampered;
    }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public boolean isTampered() { return isTampered; }
    public void setTampered(boolean isTampered) { this.isTampered = isTampered; }
}