package models;
/**
 * @author minhhien
 * Thể hiện sản phẩm trong giỏ hàng của người dùng
 */
public class CartItem {
	private long accountId;
	private long productId;
	private int quantity;

	public CartItem(long accountId, long productId, int quantity) {
		this.accountId = accountId;
		this.productId = productId;
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

}
