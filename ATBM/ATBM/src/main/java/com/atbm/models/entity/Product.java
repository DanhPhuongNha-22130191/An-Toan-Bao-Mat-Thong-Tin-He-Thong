
package com.atbm.models.entity;

public class Product {
    private long productId;
    private String name;
    private double price;
    private String description;
    private int stock;
    private byte[] image;
    private boolean isTrending;
    private double size; // Kích thước mặt đồng hồ
    private boolean waterResistance; // Chống nước
    private long brandId;
    private long strapId;
    private boolean isDeleted;

    public Product(String name, String description, String status, double price, int stock, long brandId, long strapId, double size, boolean haveTrending, boolean waterResistance, byte[] imageBytes) {
    }

    /**
     * Dùng để lấy sản phẩm
     */
    public Product(long productId, String name, double price, String description, int stock, byte[] image, boolean isTrending, double size, boolean waterResistance, long brandId, long strapId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.isTrending = isTrending;
        this.size = size;
        this.waterResistance = waterResistance;
        this.brandId = brandId;
        this.strapId = strapId;
    }

    /**
     * Tạo sản phẩm
     */
    public Product(String name, double price, String description, int stock, byte[] image, double size, long brandId, long strapId, boolean waterResistance) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.size = size;
        this.brandId = brandId;
        this.strapId = strapId;
        this.waterResistance = waterResistance;
        isDeleted = false;
        isTrending = true;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
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

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public long getStrapId() {
        return strapId;
    }

    public void setStrapId(long strapId) {
        this.strapId = strapId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
