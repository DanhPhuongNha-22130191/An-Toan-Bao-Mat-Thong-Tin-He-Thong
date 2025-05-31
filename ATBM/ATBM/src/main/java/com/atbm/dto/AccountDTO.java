package com.atbm.dto;

public class AccountDTO {
    private long accountId;
    private String username;
    private String email;
    private String publicKeyActive;

    public AccountDTO(long accountId, String username, String email) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
    }

    public AccountDTO(long accountId, String username, String email, String publicKeyActive) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.publicKeyActive = publicKeyActive;
    }

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPublicKeyActive() { return publicKeyActive; }
    public void setPublicKeyActive(String publicKeyActive) { this.publicKeyActive = publicKeyActive; }
}