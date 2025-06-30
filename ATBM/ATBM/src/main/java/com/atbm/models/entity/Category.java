package com.atbm.models.entity;

public class Category {
    private long categoryId;
    private String name;
    private String image;

    /**
     * Lấy dữ liệu
     */
    public Category(long categoryId, String name, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }

    public Category() {
    }

    /**
     * Tạo dữ liệu
     */
    public Category(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
