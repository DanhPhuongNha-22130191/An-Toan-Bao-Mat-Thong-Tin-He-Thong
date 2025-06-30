package com.atbm.models.wrapper.request;

public class CheckoutOrderRequest {
    private String recipientName;
    private String phoneNumber;
    private String addressLine;
    private int districtId;
    private int provinceId;
    private int wardId;
    private String shippingMethod;
    private String paymentMethod;
    private String note;
    private String signature;

    public CheckoutOrderRequest() {
    }

    public CheckoutOrderRequest(String recipientName, String phoneNumber, String addressLine, int districtId,
                                int provinceId, int wardId, String shippingMethod, String paymentMethod,
                                String note, String signature) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.addressLine = addressLine;
        this.districtId = districtId;
        this.provinceId = provinceId;
        this.wardId = wardId;
        this.shippingMethod = shippingMethod;
        this.paymentMethod = paymentMethod;
        this.note = note;
        this.signature = signature;
    }

    // Getters and Setters
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

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

