package dto;

public class Brand {
	private long productId;
	private long brandId;
	private String name;
	public Brand(long productId, long brandId, String name) {
		super();
		this.productId = productId;
		this.brandId = brandId;
		this.name = name;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Brand [productId=" + productId + ", brandId=" + brandId + ", name=" + name + "]";
	}

	

}
