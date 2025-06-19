package com.atbm.models.wrapper.request;

public record AddProductRequest(String name, double price, String description, int stock, byte[] image, double size,
                                long brandId, long strapId, boolean waterResistance) {
}



