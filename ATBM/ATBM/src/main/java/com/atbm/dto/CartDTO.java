
package com.atbm.dto;


import com.atbm.models.Order;
import com.atbm.models.OrderDetail;
import com.atbm.models.Product;
import com.atbm.models.Voucher;

import java.util.LinkedList;
import java.util.List;

public class CartDTO {
	private List<CartItemDTO> items;
	private Voucher voucher;
	private OrderDetail orderDetail;
	private Order order;

	public CartDTO() {
		items = new LinkedList<CartItemDTO>();
	}

	public void add(Product Product, long cartItemId, int quantity) {
		CartItemDTO dto = new CartItemDTO(cartItemId, Product.getProductId(), Product.getName(), Product.getImage(),
				Product.getPrice(), quantity);
		items.add(dto);
	}


	public void setOrder(Order order) {
		this.order = order;
	}

	public long getOrderId() {
		return order.getOrderId();
	}

	public double getShipping() {
		return order.getShipping();
	}

	public String getPaymentMethod() {
		return order.getPaymentMethod();
	}

	public String getOrderNote() {
		return orderDetail.getOrderNote();
	}

	public String getAddress() {
		return orderDetail.getAddress();
	}

	public String getEmail() {
		return orderDetail.getEmail();
	}

	public String getPhone() {
		return orderDetail.getPhone();
	}

	public String getFullName() {
		return orderDetail.getFullName();
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public long getVoucherId() {
		return voucher.getVoucherId();
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void addAll(List<CartItemDTO> listItems) {
		items.addAll(listItems);
	}

	public double getSubTotal() {
		return items.stream().mapToDouble(e -> e.getTotalPrice()).sum();
	}

	public double getTotalPrice() {
		return getSubTotal() - getDiscount();
	}

	public double getDiscount() {
		return (getSubTotal() * (voucher == null ? 0 : voucher.getPercentDescrease())) / 100;
	}

	public void addVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public class CartItemDTO {
		private long cartItemId;
		private long ProductId;
		private String ProductName;
		private String ProductImg;
		private double ProductPrice;
		private int quantity;
		private double price;

		public CartItemDTO() {
		}

		public CartItemDTO(long cartItemId, long ProductId, String ProductName, String ProductImg, double ProductPrice,
				int quantity) {
			this.cartItemId = cartItemId;
			this.ProductId = ProductId;
			this.ProductName = ProductName;
			this.ProductImg = ProductImg;
			this.ProductPrice = ProductPrice;
			this.quantity = quantity;
		}

		public long getCartItemId() {
			return cartItemId;
		}

		public void setCartItemId(long cartItemId) {
			this.cartItemId = cartItemId;
		}

		public double getTotalPrice() {
			return quantity * ProductPrice;
		}

		public long getProductId() {
			return ProductId;
		}

		public void setProductId(long ProductId) {
			this.ProductId = ProductId;
		}

		public String getProductName() {
			return ProductName;
		}

		public void setProductName(String ProductName) {
			this.ProductName = ProductName;
		}

		public String getProductImg() {
			return ProductImg;
		}

		public void setProductImg(String ProductImg) {
			this.ProductImg = ProductImg;
		}

		public double getProductPrice() {
			return ProductPrice;
		}

		public void setProductPrice(double ProductPrice) {
			this.ProductPrice = ProductPrice;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public double getPrice() { return price; }
		public void setPrice(double price) { this.price = price; }
    }
}


