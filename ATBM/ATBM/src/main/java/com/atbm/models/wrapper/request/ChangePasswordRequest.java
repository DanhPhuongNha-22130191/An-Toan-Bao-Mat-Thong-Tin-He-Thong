package com.atbm.models.wrapper.request;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    // Constructor mặc định (yêu cầu bởi BeanUtils.populate)
    public ChangePasswordRequest() {
    }

    // Constructor đầy đủ
    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    // Getter và Setter
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}