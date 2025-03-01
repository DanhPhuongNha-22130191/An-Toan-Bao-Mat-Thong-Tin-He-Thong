package dto;

import java.util.LinkedList;
import java.util.List;

import models.Product;
import models.Voucher;

public class CartDTO {
	private List<CartItemDTO> items;
	private Voucher voucher;

	public CartDTO() {
		items = new LinkedList<CartDTO.CartItemDTO>();
	}

	public void add(Product product, int quantity) {
		items.add(new CartItemDTO(product.getProductId(), product.getName(), product.getImage(), product.getPrice(),
				quantity));
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
		return getSubTotal() * voucher.getPercentDescrease();
	}

	public void addVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public class CartItemDTO {
		private long productId;
		private String productName;
		private String productImg;
		private double productPrice;
		private int quantity;

		public CartItemDTO(long productId, String productName, String productImg, double productPrice, int quantity) {
			this.productId = productId;
			this.productName = productName;
			this.productImg = productImg;
			this.productPrice = productPrice;
			this.quantity = quantity;
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
