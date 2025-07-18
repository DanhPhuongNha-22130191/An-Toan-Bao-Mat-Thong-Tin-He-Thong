package com.atbm.models.entity;

public class ShippingInfo {
    private long shippingInfoId;
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String district;
    private String province;
    private String ward;
    private String note;
    private String shippingMethod;
    private double shippingFee;

    private long provinceId;
    private long districtId;
    private long wardId;

    public ShippingInfo(long shippingInfoId, String recipientName, String phoneNumber, String addressLine, String district, String province, String ward, String note, String shippingMethod, double shippingFee, long provinceId, long districtId, long wardId) {
        this.shippingInfoId = shippingInfoId;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.addressLine = addressLine;
        this.district = district;
        this.province = province;
        this.ward = ward;
        this.note = note;
        this.shippingMethod = shippingMethod;
        this.shippingFee = shippingFee;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
    }

    /**
     *  Lấy dữ liệu
     */


    public ShippingInfo() {
    }

    /**
     *  Tạo dữ liệu
     */
    public ShippingInfo(String recipientName, String phoneNumber, String addressLine, String district, String province, String ward, String note, String shippingMethod, double shippingFee, long provinceId, long districtId, long wardId) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.addressLine = addressLine;
        this.district = district;
        this.ward = ward;
        this.province = province;
        this.note = note;
        this.shippingMethod = shippingMethod;
        this.shippingFee = shippingFee;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
    }


    public long getShippingInfoId() {
        return shippingInfoId;
    }

    public void setShippingInfoId(long shippingInfoId) {
        this.shippingInfoId = shippingInfoId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
}
