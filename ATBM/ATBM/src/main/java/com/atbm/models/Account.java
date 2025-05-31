package com.atbm.models;
import java.util.List;

public class Account {
	private long accountId;
	private String username;
	private String password;
	private String email;
    private String publicKeyActive;

    public Account() {}

	public Account(long accountId, String username, String password, String email, String publicKeyActive) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.publicKeyActive = publicKeyActive;
	}
    public Account(long accountId,String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;

    }
    public Account(String username, String password, String email, String publicKeyActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.publicKeyActive = publicKeyActive;
    }
    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

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

    public String getPublicKeyActive() { return publicKeyActive; }
    public void setPublicKeyActive(String publicKeyActive) {
        this.publicKeyActive = publicKeyActive;
    }
}