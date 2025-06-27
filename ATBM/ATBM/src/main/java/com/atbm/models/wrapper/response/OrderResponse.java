package com.atbm.models.wrapper.response;

import com.atbm.models.entity.Order;
import com.atbm.models.entity.OrderItem;
import com.atbm.models.entity.ShippingInfo;

import java.util.List;

public class OrderResponse{
    private Order order;
    private ShippingInfo shippingInfo;
    private List<OrderItem> orderItems;
    private boolean isDigitallySigned;

    public OrderResponse() {
    }

    public OrderResponse(Order order, ShippingInfo shippingInfo, List<OrderItem> orderItems, boolean isDigitallySigned) {
        this.order = order;
        this.shippingInfo = shippingInfo;
        this.orderItems = orderItems;
        this.isDigitallySigned = isDigitallySigned;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isDigitallySigned() {
        return isDigitallySigned;
    }

    public void setDigitallySigned(boolean digitallySigned) {
        isDigitallySigned = digitallySigned;
    }


}
