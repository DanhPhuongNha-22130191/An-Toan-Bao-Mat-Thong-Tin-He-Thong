package models;

public class Order {
	private long orderId;
	private long accountId;
	private double shipping;
	private String paymentMethod;
	private long voucherId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public Order(long orderId, long accountId, double shipping, String paymentMethod, long voucherId) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.shipping = shipping;
		this.paymentMethod = paymentMethod;
		this.voucherId = voucherId;
	}

	public Order() {
	}

}
