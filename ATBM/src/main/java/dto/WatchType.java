package dto;

public class WatchType {
	private long productId; 
	private long typeId;
	private String name;

	public WatchType(long productId, long typeId, String name) {
		super();
		this.productId = productId;
		this.typeId = typeId;
		this.name = name;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
