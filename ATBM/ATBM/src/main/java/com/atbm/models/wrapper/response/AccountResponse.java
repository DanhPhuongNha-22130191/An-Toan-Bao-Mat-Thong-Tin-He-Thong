package com.atbm.models.wrapper.response;

import com.atbm.models.enums.Role;

public class AccountResponse {
    private long accountId;
    private String username;
    private String email;
    private String publicKeyActive;
    private Role role;

    public AccountResponse(long accountId, String username, String email, String publicKeyActive, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.publicKeyActive = publicKeyActive;
        this.role = role;
    }

    public AccountResponse() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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

    public String getPublicKeyActive() {
        return publicKeyActive;
    }

    public void setPublicKeyActive(String publicKeyActive) {
        this.publicKeyActive = publicKeyActive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}