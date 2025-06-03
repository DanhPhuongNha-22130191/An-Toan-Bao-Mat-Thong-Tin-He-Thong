package com.atbm.models;

public class Strap {

    private long strapId;
    private String color;
    private String material;
    private double length;

    public Strap(long strapId, String color, String material, double length) {
        this.strapId = strapId;
        this.color = color;
        this.material = material;
        this.length = length;
    }

    public Strap() {

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
        return "Strap [strapId=" + strapId + ", color=" + color + ", material=" + material
                + ", length=" + length + "]";
    }

}
