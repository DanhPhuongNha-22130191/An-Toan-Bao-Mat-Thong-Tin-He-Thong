package models;

public class Product {
	private long productId;
	private String name;
	private double price;
	private String description;
	private int stock;
	private String image;
	private boolean haveTrending;
	private double size; // Kích thước mặt đồng hồ
	private boolean waterResistance; // Chống nước

	public Product(long l, String string, double d, String string2, int i, String string3, boolean b, double e, boolean c, double f, long m) {
	}

	public Product(long productId, String name, double price, String description, int stock, String image,
			boolean haveTrending, double size, boolean waterResistance, double batteryLife) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.stock = stock;
		this.image = image;
		this.haveTrending = haveTrending;
		this.size = size;
		this.waterResistance = waterResistance;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
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

	public boolean isHaveTrending() {
		return haveTrending;
	}

	public void setHaveTrending(boolean haveTrending) {
		this.haveTrending = haveTrending;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public boolean isWaterResistance() {
		return waterResistance;
	}

	public void setWaterResistance(boolean waterResistance) {
		this.waterResistance = waterResistance;
	}



	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", price=" + price + ", description="
				+ description + ", stock=" + stock + ", image=" + image + ", haveTrending=" + haveTrending + ", size="
				+ size + ", waterResistance=" + waterResistance + ", batteryLife=" +  "]";
	}
	

}