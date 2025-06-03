package com.atbm.models;

public class Brand {
    private long brandId;
    private String name;

    public Brand(long brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public Brand() {

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

    @Override
    public String toString() {
        return "Brand [brandId=" + brandId + ", name=" + name + "]";
    }


}
