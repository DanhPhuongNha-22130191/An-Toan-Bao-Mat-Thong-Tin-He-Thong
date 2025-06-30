package com.atbm.models.entity;

import com.atbm.models.enums.Role;

public class Account {
    private long accountId;
    private String username;
    private String password;
    private String email;
    private String publicKeyActive;
    private Role role;
    private boolean isDeleted;

    public Account() {
        this.isDeleted = false;
    }

    /**
     * Constructor dùng tạo tài khoản
     */
    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.USER;
    }

    /**
     * Constructor dùng lấy dữ liệu
     */
    public Account(long accountId, String username, String password, String email, String publicKeyActive, Role role, boolean isDeleted) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.publicKeyActive = publicKeyActive;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    // Getter & Setter cho tất cả thuộc tính
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
