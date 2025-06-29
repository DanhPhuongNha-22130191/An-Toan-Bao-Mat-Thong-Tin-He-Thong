package com.atbm.models.wrapper.request;

public class UpdateProfileRequest{
    private String username;
    private String email;

    public UpdateProfileRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UpdateProfileRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}