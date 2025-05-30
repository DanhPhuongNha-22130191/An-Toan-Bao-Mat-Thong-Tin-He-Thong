package com.atbm.models;

import com.atbm.dto.CartDTO;
import java.util.Date;

public class Order {
	private long orderId;
	private long accountId;
	private double shipping;
	private String paymentMethod;
	private Long voucherId;
	private CartDTO cartDTO;
	private OrderDetail orderDetail;
	private Date orderDate;
	private double totalAmount;

	public Order() {}

	public Order(long orderId, long accountId, double shipping, String paymentMethod, Long voucherId) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.shipping = shipping;
		this.paymentMethod = paymentMethod;
		this.voucherId = voucherId;
	}

	public Order(long accountId, double shipping, String paymentMethod, CartDTO cartDTO, OrderDetail orderDetail) {
		this.accountId = accountId;
		this.shipping = shipping;
		this.paymentMethod = paymentMethod;
		this.cartDTO = cartDTO;
		this.orderDetail = orderDetail;
	}

	public long getOrderId() { return orderId; }
	public void setOrderId(long orderId) { this.orderId = orderId; }
	public long getAccountId() { return accountId; }
	public void setAccountId(long accountId) { this.accountId = accountId; }
	public double getShipping() { return shipping; }
	public void setShipping(double shipping) { this.shipping = shipping; }
	public String getPaymentMethod() { return paymentMethod; }
	public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
	public Long getVoucherId() { return voucherId; }
	public void setVoucherId(Long voucherId) { this.voucherId = voucherId; }
	public CartDTO getCartDTO() { return cartDTO; }
	public void setCartDTO(CartDTO cartDTO) { this.cartDTO = cartDTO; }
	public OrderDetail getOrderDetail() { return orderDetail; }
	public void setOrderDetail(OrderDetail orderDetail) { this.orderDetail = orderDetail; }
	public Date getOrderDate() { return orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
	public double getTotalAmount() { return totalAmount; }
	public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}