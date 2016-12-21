package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaIn extends BaseEntity{
    private String ring;
    private Date doTime;
    private Double totalPrice;//消费
    private String userId;
    private String saunaGroupSerial;
    private String inCategory;//账单类型

    public SaunaIn() {
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSaunaGroupSerial() {
        return saunaGroupSerial;
    }

    public void setSaunaGroupSerial(String saunaGroupSerial) {
        this.saunaGroupSerial = saunaGroupSerial;
    }

    public String getInCategory() {
        return inCategory;
    }

    public void setInCategory(String inCategory) {
        this.inCategory = inCategory;
    }
}
