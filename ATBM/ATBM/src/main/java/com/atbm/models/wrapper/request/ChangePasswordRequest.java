package com.atbm.models.wrapper.request;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}