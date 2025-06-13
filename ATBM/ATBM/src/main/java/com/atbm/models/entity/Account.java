package com.atbm.models.entity;

import com.atbm.models.enums.Role;

public class Account {
    private long accountId;
    private String username;
    private String password;
    private String email;
    private String publicKey;
    private Role role;

    /**
     * Constructor dùng tạo tài khoản
     */
    public Account(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = Role.USER;
    }

    public Account() {
    }

    /**
     * Constructor dùng lấy dữ liệu
     */
    public Account(long accountId, String username, String password, String email, String publicKey, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.publicKey = publicKey;
        this.role = role;
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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}