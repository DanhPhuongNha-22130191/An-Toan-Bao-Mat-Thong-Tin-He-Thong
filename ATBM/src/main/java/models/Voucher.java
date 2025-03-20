
package models;

import java.sql.Date;

public class Voucher {
	private long voucherId;
	private String code;
	private Date expirationTime;
	private double percentDescrease;
	private String name;
	private int quantity;

	public Voucher(long voucherId, String code, Date expirationTime, double percentDescrease, String name,
			int quantity) {
		this.voucherId = voucherId;
		this.code = code;
		this.expirationTime = expirationTime;
		this.percentDescrease = percentDescrease;
		this.name = name;
		this.quantity = quantity;
	}
	public Voucher( String code, Date expirationTime, double percentDescrease, String name,
			int quantity) {
		this.code = code;
		this.expirationTime = expirationTime;
		this.percentDescrease = percentDescrease;
		this.name = name;
		this.quantity = quantity;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public double getPercentDescrease() {
		return percentDescrease;
	}

	public void setPercentDescrease(double percentDescrease) {
		this.percentDescrease = percentDescrease;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
=======
package models;

import java.sql.Date;

public class Voucher {
	private long voucherId;
	private String code;
	private Date expirationTime;
	private double percentDescrease;
	private String name;
	private int quantity;

	public Voucher(long voucherId, String code, Date expirationTime, double percentDescrease, String name,
			int quantity) {
		this.voucherId = voucherId;
		this.code = code;
		this.expirationTime = expirationTime;
		this.percentDescrease = percentDescrease;
		this.name = name;
		this.quantity = quantity;
	}
	public Voucher( String code, Date expirationTime, double percentDescrease, String name,
			int quantity) {
		this.code = code;
		this.expirationTime = expirationTime;
		this.percentDescrease = percentDescrease;
		this.name = name;
		this.quantity = quantity;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public double getPercentDescrease() {
		return percentDescrease;
	}

	public void setPercentDescrease(double percentDescrease) {
		this.percentDescrease = percentDescrease;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}

