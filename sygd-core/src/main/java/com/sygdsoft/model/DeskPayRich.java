package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-03-16.
 */
public class DeskPayRich extends BaseEntity {
    private Double payMoney;
    private String currency;
    private String currencyAdd;
    private String ckSerial;
    private Date doTime;
    private Date doneTime;
    private Double totalPrice;
    private Double finalPrice;
    private Double discount;
    private String desk;
    private String userId;
    private String pointOfSale;
    private Integer num;
    private Boolean disabled;

    public DeskPayRich() {
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyAdd() {
        return currencyAdd;
    }

    public void setCurrencyAdd(String currencyAdd) {
        this.currencyAdd = currencyAdd;
    }

    public String getCkSerial() {
        return ckSerial;
    }

    public void setCkSerial(String ckSerial) {
        this.ckSerial = ckSerial;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
