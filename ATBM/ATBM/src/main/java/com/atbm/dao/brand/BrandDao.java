package com.atbm.dao.brand;

import com.atbm.models.entity.Brand;

import java.util.List;

public interface BrandDao {
    Brand getBrandById(long brandId);
    List<Brand> getBrands();
}
