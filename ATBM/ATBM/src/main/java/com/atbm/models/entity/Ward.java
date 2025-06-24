package com.atbm.models.entity;

public class Ward {
    private long id;
    private String name;
    private long districtId;
    private int code;

    public Ward() {
    }

    public Ward(String name, long districtId, int code) {
        this.name = name;
        this.districtId = districtId;
        this.code = code;
    }

    public Ward(long id, String name, long districtId, int code) {
        this.id = id;
        this.name = name;
        this.districtId = districtId;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
