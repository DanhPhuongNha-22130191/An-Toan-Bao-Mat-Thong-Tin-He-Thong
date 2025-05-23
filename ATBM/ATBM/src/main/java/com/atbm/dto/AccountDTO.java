package dto;

public class AccountDTO {
    private long accountId; 
    private String username;
    private String email;

    public AccountDTO(long accountId, String username, String email) {
        this.accountId = accountId;
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
