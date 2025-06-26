package com.atbm.dao.brand;

import com.atbm.models.entity.Brand;

import java.util.List;

public interface BrandDao {
    String BRAND_ID = "brandId";
    String NAME = "name";

    Brand getBrandById(long brandId);
    List<Brand> getBrands();
}
