package com.atbm.models.wrapper.request;

public record EditProductRequest(long productId,
                                 String name,
                                 double price,
                                 String description,
                                 int stock,
                                 byte[] image,
                                 long brandId
) {
}
