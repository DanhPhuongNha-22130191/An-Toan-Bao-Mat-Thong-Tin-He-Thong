package com.atbm.models.entity;

public class Brand {
    private long brandId;
    private String name;

    /**
     * Lấy dữ liệu
     */
    public Brand(long brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }
    public Brand() {
    }

    /**
     * Tạo brand
     */
    public Brand(String name) {
        this.name = name;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
