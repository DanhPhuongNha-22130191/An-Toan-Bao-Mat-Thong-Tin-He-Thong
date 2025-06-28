package com.atbm.models.entity;

import java.util.List;

public class District {
    private long id;
    private long provinceId;
    private String name;
    private int code;
    private List<Ward> wards;

    public District() {
    }

    /**
     * Tạo dữ liệu
     */
    public District(long provinceId, String name, int code) {
        this.provinceId = provinceId;
        this.name = name;
        this.code = code;
    }

    /**
     * Lấy dữ liệu
     */
    public District(long id, long provinceId, String name, int code) {
        this.id = id;
        this.provinceId = provinceId;
        this.name = name;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", wards=" + wards +
                '}';
    }
}
