package models;

public class Account {
	private long accountId;
	private String username;
	private String password;
	private String email;

	public Account(long accountId, String username, String password, String email) {
		super();
		this.accountId = accountId;
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

}
