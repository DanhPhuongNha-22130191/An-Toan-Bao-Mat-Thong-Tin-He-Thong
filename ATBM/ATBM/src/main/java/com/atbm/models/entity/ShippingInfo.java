package com.atbm.models.entity;

public class ShippingInfo {
    private long shippingInfoId;
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private String city;
    private String district;
    private String ward;
    private String note;
    private String shippingMethod;
    private String shippingFee;

    /**
     *  Lấy dữ liệu
     */
    public ShippingInfo(long shippingInfoId, String recipientName, String phoneNumber, String addressLine, String city, String district, String ward, String note, String shippingMethod, String shippingFee) {
        this.shippingInfoId = shippingInfoId;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.addressLine = addressLine;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.note = note;
        this.shippingMethod = shippingMethod;
        this.shippingFee = shippingFee;
    }

    public ShippingInfo() {
    }

    /**
     *  Tạo dữ liệu
     */
    public ShippingInfo(String shippingFee, String shippingMethod, String note, String ward, String district, String city, String addressLine, String phoneNumber, String recipientName) {
        this.shippingFee = shippingFee;
        this.shippingMethod = shippingMethod;
        this.note = note;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.addressLine = addressLine;
        this.phoneNumber = phoneNumber;
        this.recipientName = recipientName;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }
}
