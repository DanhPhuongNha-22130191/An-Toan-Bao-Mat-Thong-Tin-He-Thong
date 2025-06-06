package com.atbm.models;

/**
 * @author minhhien Thể hiện sản phẩm trong giỏ hàng của người dùng
 */
public class CartItem {
	private long cartItemId;
	private long accountId;
	private long productId;
	private Long orderId; // Sửa thành Long
	private int quantity;

	public CartItem(long cartItemId, long productId, int quantity) {
		this.cartItemId = cartItemId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public CartItem(long cartItemId, int quantity) {
		this.cartItemId = cartItemId;
		this.quantity = quantity;
	}

	public CartItem(long cartItemId, long accountId, long productId, int quantity) {
		this.cartItemId = cartItemId;
		this.accountId = accountId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public CartItem(long cartItemId, long accountId, long productId, Long orderId, int quantity) {
		this.cartItemId = cartItemId;
		this.accountId = accountId;
		this.productId = productId;
		this.orderId = orderId;
		this.quantity = quantity;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}
}