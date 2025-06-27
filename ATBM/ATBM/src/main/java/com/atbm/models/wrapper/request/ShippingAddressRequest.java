package com.atbm.models.wrapper.request;

public record ShippingAddressRequest(String recipientName,
                                     String phoneNumber,
                                     int provinceId,
                                     int districtId,
                                     int wardId,
                                     String note,
                                     String shippingMethod,
                                     double shippingFee) {
}
