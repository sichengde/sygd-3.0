package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-01-13.
 */
public class TelDetail extends BaseEntity{
    private String telId;
    private Date telTime;
    private Integer duration;
    private Double price;
    private String targetNumber;
    private Double totalPrice;

    public TelDetail() {
    }

    public String getTelId() {
        return telId;
    }

    public void setTelId(String telId) {
        this.telId = telId;
    }

    public Date getTelTime() {
        return telTime;
    }

    public void setTelTime(Date telTime) {
        this.telTime = telTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
