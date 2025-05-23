package com.atbm.models;

import java.sql.Date;

public class Voucher {
    private long voucherId;
    private String code;
    private Date expirationTime;
    private double percentDecrease;
    private String name;
    private int quantity;

    public Voucher() {
    }

    public Voucher(long voucherId, String code, Date expirationTime, double percentDecrease, String name,
                   int quantity) {
        this.voucherId = voucherId;
        this.code = code;
        this.expirationTime = expirationTime;
        this.percentDecrease = percentDecrease;
        this.name = name;
        this.quantity = quantity;
    }

    public Voucher(String code, Date expirationTime, double percentDecrease, String name,
                   int quantity) {
        this.code = code;
        this.expirationTime = expirationTime;
        this.percentDecrease = percentDecrease;
        this.name = name;
        this.quantity = quantity;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public double getPercentDecrease() {
        return percentDecrease;
    }

    public void setPercentDecrease(double percentDecrease) {
        this.percentDecrease = percentDecrease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }




    public boolean isValid() {
        Date now = new Date(System.currentTimeMillis());

        return
                now.before(expirationTime) &&
                        quantity > 0 &&
                        percentDecrease > 0 && percentDecrease <= 100;
    }

    public boolean isExpired() {
        return new Date(System.currentTimeMillis()).after(expirationTime);
    }

    public boolean hasRemainingQuantity() {
        return quantity > 0;
    }

}


