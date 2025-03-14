package dto;

import java.util.List;

public class OrderDTO {
	private long orderId;
	private double shipping;
	private String paymentMethod;
	private String address;
	private String orderNote;
	private List<OrderItemDTO> items;
	

	public OrderDTO(long orderId, double shipping, String paymentMethod, String address, String orderNote,
			List<OrderItemDTO> items) {
		this.orderId = orderId;
		this.shipping = shipping;
		this.paymentMethod = paymentMethod;
		this.address = address;
		this.orderNote = orderNote;
		this.items = items;
	}


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getOrderNote() {
		return orderNote;
	}


	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}


	public List<OrderItemDTO> getItems() {
		return items;
	}


	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}


	public class OrderItemDTO {
		private String productName;
		private double productPrice;
		private int quanity;
		public OrderItemDTO(String productName, double productPrice, int quanity) {
			this.productName = productName;
			this.productPrice = productPrice;
			this.quanity = quanity;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public double getProductPrice() {
			return productPrice;
		}
		public void setProductPrice(double productPrice) {
			this.productPrice = productPrice;
		}
		public int getQuanity() {
			return quanity;
		}
		public void setQuanity(int quanity) {
			this.quanity = quanity;
		}
		
	}

}
