package dto;

public class Strap {
    private long productId; 
    private long strapId;
    private String color;
    private String material;
    private double length; 
	public Strap(long productId, long strapId, String color, String material, double length) {
		super();
		this.productId = productId;
		this.strapId = strapId;
		this.color = color;
		this.material = material;
		this.length = length;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getStrapId() {
		return strapId;
	}
	public void setStrapId(long strapId) {
		this.strapId = strapId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "Strap [productId=" + productId + ", strapId=" + strapId + ", color=" + color + ", material=" + material
				+ ", length=" + length + "]";
	}

}
