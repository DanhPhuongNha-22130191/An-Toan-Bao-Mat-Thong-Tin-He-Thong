package com.atbm.models;


import com.atbm.dto.CartDTO;

public class Order {
	private long orderId;
	private long accountId;
	private double shipping;
	private String paymentMethod;
	private Long voucherId;
	private CartDTO cartDTO;
	private models.OrderDetail orderDetail;

	public Order(long accountId, double shipping, String paymentMethod,  CartDTO cartDTO,
			models.OrderDetail orderDetail) {
		this.accountId = accountId;
		this.shipping = shipping;
		this.paymentMethod = paymentMethod;
		this.cartDTO = cartDTO;
		this.orderDetail = orderDetail;
	}

	public CartDTO getCartDTO() {
		return cartDTO;
	}

	public void setCartDTO(CartDTO cartDTO) {
		this.cartDTO = cartDTO;
	}

	public models.OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(models.OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

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

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(long voucherId) {
		this.voucherId = voucherId;
	}

	public Order(long accountId, double shipping, String paymentMethod) {
		this.accountId = accountId;
		this.shipping = shipping;
		this.paymentMethod = paymentMethod;
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
