package com.atbm.models.wrapper.response;

import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Strap;

public record ProductResponse(long productId,
                              String name,
                              double price,
                              String description,
                              int stock,
                              byte[] image,
                              Strap strap,
                              Brand brand,
                              boolean waterResistance,
                              double size
) {

}
