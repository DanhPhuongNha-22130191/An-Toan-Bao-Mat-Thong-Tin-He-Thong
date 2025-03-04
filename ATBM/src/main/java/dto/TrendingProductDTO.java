package dto;

public class TrendingProductDTO {
	private long productId;
	private long categoryId;
	private String name;
	private double price;
	private String description;
	private int stock;
	private String image;

	public TrendingProductDTO(long productId, long categoryId, String name, double price, String description, int stock,
			String image) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.stock = stock;
		this.image = image;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
