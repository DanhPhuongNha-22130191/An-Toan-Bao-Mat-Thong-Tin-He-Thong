package dto;

import java.util.LinkedList;
import java.util.List;

import models.Order;
import models.OrderDetail;
import models.Product;
import models.Voucher;

public class CartDTO {
	private List<CartItemDTO> items;
	private Voucher voucher;
	private OrderDetail orderDetail;
	private Order order;

	public CartDTO() {
		items = new LinkedList<CartDTO.CartItemDTO>();
	}

	public void add(Product product, long cartItemId, int quantity) {
		CartItemDTO dto = new CartItemDTO(cartItemId, product.getProductId(), product.getName(), product.getImage(),
				product.getPrice(), quantity);
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
		private long productId;
		private String productName;
		private String productImg;
		private double productPrice;
		private int quantity;

		public CartItemDTO() {
		}

		public CartItemDTO(long cartItemId, long productId, String productName, String productImg, double productPrice,
				int quantity) {
			this.cartItemId = cartItemId;
			this.productId = productId;
			this.productName = productName;
			this.productImg = productImg;
			this.productPrice = productPrice;
			this.quantity = quantity;
		}

		public long getCartItemId() {
			return cartItemId;
		}

		public void setCartItemId(long cartItemId) {
			this.cartItemId = cartItemId;
		}

		public double getTotalPrice() {
			return quantity * productPrice;
		}

		public long getProductId() {
			return productId;
		}

		public void setProductId(long productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductImg() {
			return productImg;
		}

		public void setProductImg(String productImg) {
			this.productImg = productImg;
		}

		public double getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(double productPrice) {
			this.productPrice = productPrice;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

	}
}
