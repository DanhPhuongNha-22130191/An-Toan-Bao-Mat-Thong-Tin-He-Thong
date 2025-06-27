package com.atbm.models.wrapper.response;

import com.atbm.models.entity.Brand;
import com.atbm.models.entity.Strap;

public class ProductResponse {
    private long productId;
    private String name;
    private double price;
    private String description;
    private int stock;
    private byte[] image;
    private Strap strap;
    private Brand brand;
    private double size;

    public ProductResponse() {
        // constructor mặc định
    }

    public ProductResponse(long productId, String name, double price, String description, int stock,
                           byte[] image, Strap strap, Brand brand, double size) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.strap = strap;
        this.brand = brand;
        this.size = size;
    }

    // --- Getter và Setter ---

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

    public Strap getStrap() {
        return strap;
    }

    public void setStrap(Strap strap) {
        this.strap = strap;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
