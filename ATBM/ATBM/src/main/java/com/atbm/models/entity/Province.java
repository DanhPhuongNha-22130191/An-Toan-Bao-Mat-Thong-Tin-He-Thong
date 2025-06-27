package com.atbm.models.entity;

import java.util.List;

public class Province {
    private long id;
    private int code;
    private String name;
    private List<District> districts;

    public Province() {
    }

    public Province(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Province(long id, int code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", districts=" + districts +
                '}';
    }
}
