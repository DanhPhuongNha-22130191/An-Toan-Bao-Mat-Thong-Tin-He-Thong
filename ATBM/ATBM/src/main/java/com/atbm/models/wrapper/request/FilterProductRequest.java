package com.atbm.models.wrapper.request;

import java.util.Set;

public record FilterProductRequest(Set<Long> brandsId, Set<Long> strapsId, Double minPrice,
                                   Double maxPrice) {
}
