package com.atbm.dto;

public class ProductDTO {
	private long productId;
	private long categoryId;
	private String name;
	private double price;
	private String description;
	private String image;
	private boolean haveTrending;
	

	public ProductDTO() {
	}

	public ProductDTO(long productId, String name, double price, String description, String image,
			boolean haveTrending) {
		this.productId = productId;

		this.name = name;
		this.price = price;
		this.description = description;
		this.image = image;
		this.haveTrending = haveTrending;
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
}
