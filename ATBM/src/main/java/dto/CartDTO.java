package dto;

import java.util.List;

public class CartDTO {
	private List<CartItemDTO> items;

	public void add(CartItemDTO item) {
		items.add(item);
	}

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void addAll(List<CartItemDTO> listItems) {
		items.addAll(listItems);
	}
	public double getSubTotal() {
		return items.stream().mapToDouble(e->e.getTotalPrice()).sum();
	}
	public class CartItemDTO {
		private long productId;
		private String productName;
		private String productImg;
		private double productPrice;
		private double quantity;

		public CartItemDTO(long productId, String productName, String productImg, double productPrice,
				double quantity) {
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

		public double getQuantity() {
			return quantity;
		}

		public void setQuantity(double quantity) {
			this.quantity = quantity;
		}

	}
}
