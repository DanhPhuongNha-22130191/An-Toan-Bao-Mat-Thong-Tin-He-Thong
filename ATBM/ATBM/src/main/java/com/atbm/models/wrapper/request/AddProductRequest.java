package com.atbm.models.wrapper.request;

public class AddProductRequest {
    private String name;
    private double price;
    private String description;
    private int stock;
    private byte[] image;
    private double size;
    private long brandId;
    private long strapId;
    private boolean waterResistance;

    public AddProductRequest() {
    }

    public AddProductRequest(String name, double price, String description, int stock, byte[] image,
                             double size, long brandId, long strapId, boolean waterResistance) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.size = size;
        this.brandId = brandId;
        this.strapId = strapId;
        this.waterResistance = waterResistance;
    }

    // Getters và setters

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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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

    public boolean isWaterResistance() {
        return waterResistance;
    }

    public void setWaterResistance(boolean waterResistance) {
        this.waterResistance = waterResistance;
    }
}
