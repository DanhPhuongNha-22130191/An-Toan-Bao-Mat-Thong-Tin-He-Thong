package com.atbm.models;

import java.sql.Date;

public class UsedVouchers {
	private long accountId;
	private String code;
	private Date usedAt;
	public UsedVouchers(long accountId, String code, Date usedAt) {
		super();
		this.accountId = accountId;
		this.code = code;
		this.usedAt = usedAt;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getUsedAt() {
		return usedAt;
	}
	public void setUsedAt(Date usedAt) {
		this.usedAt = usedAt;
	}
	
}
