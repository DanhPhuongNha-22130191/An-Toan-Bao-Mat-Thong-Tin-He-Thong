package com.atbm.models.wrapper.response;

import com.atbm.models.entity.Order;
import com.atbm.models.entity.OrderItem;
import com.atbm.models.entity.ShippingInfo;

import java.util.List;

public record OrderResponse(Order order, ShippingInfo shippingInfo, List<OrderItem> orderItems,
                            boolean isDigitallySigned) {
}
