package com.atbm.models.wrapper.request;

public record CheckoutOrderRequest(String recipientName, String phoneNumber, String addressLine, int districtId,
                                   int provinceId, int wardId, String shippingMethod, String paymentMethod, String note,
                                   String signature) {
}
