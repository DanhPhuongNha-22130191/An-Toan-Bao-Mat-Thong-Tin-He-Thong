package com.atbm.models.wrapper.request;

public class CalculateShippingRequest {
    private long provinceId;
    private long districtId;
    private long wardId;
    private String shippingMethod;

    public CalculateShippingRequest(long provinceId, long districtId, long wardId, String shippingMethod) {
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.shippingMethod = shippingMethod;
    }

    public CalculateShippingRequest() {
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public long getWardId() {
        return wardId;
    }

    public void setWardId(long wardId) {
        this.wardId = wardId;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
