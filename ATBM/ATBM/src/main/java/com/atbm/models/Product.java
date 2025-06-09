package com.atbm.models;

public class Product {
    private long productId;
    private String name;
    private double price;
    private String description;
    private int stock;
    private byte[] image;
    private boolean haveTrending;
    private double size; // Kích thước mặt đồng hồ
    private boolean waterResistance; // Chống nước
    private long brandId;
    private long strapId;
    private String status;
    private boolean isDeleted;

    public Product() {
    }
    public Product(long productId, String name, double price, String description, int stock, byte[] image, long brandId, String status) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.brandId = brandId;
        this.status = status;
    }

    public Product(long productId, String name, double price, String description, int stock, byte[] image, boolean haveTrending, double size, boolean waterResistance, long brandId, long strapId, String status, boolean isDeleted) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.haveTrending = haveTrending;
        this.size = size;
        this.waterResistance = waterResistance;
        this.brandId = brandId;
        this.strapId = strapId;
        this.status = status;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public byte[] getImage() {
        return image;
    }

    public boolean isHaveTrending() {
        return haveTrending;
    }

    public double getSize() {
        return size;
    }

    public boolean isWaterResistance() {
        return waterResistance;
    }

    public long getBrandId() {
        return brandId;
    }

    public long getStrapId() {
        return strapId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setHaveTrending(boolean haveTrending) {
        this.haveTrending = haveTrending;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setWaterResistance(boolean waterResistance) {
        this.waterResistance = waterResistance;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public void setStrapId(long strapId) {
        this.strapId = strapId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", haveTrending=" + haveTrending +
                ", size=" + size +
                ", waterResistance=" + waterResistance +
                ", brandId=" + brandId +
                ", strapId=" + strapId +
                ", status=" + status +
                '}';
    }
}