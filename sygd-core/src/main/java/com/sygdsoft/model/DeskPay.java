package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class DeskPay extends BaseEntity{
    private Date doneTime;
    private Double payMoney;
    private String currency;
    private String currencyAdd;
    private String ckSerial;
    private String userId;
    private String pointOfSale;
    private String company;
    private Boolean disabled;//是否可用状态，例如使用自助餐冲减之后，该值则为true，表示该条记录忽略

    public DeskPay() {
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
