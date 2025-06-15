package com.atbm.models.entity;

public class Strap {
    private long strapId;
    private String color;
    private String material;
    private double length;

    /**
     * Tạo Strap
     */
    public Strap(String color, String material, double length) {
        this.color = color;
        this.material = material;
        this.length = length;
    }

    public Strap() {
    }

    /**
     * Lấy dữ liệu
     */
    public Strap(double length, String material, String color, long strapId) {
        this.length = length;
        this.material = material;
        this.color = color;
        this.strapId = strapId;
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
}
