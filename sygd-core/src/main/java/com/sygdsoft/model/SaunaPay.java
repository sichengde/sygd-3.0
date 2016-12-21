package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaPay extends BaseEntity{
    private Date doneTime;
    private Double payMoney;
    private String currency;
    private String currencyAdd;
    private String saunaSerial;
    private String userId;
    private Boolean disabled;

    public SaunaPay() {
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

    public String getSaunaSerial() {
        return saunaSerial;
    }

    public void setSaunaSerial(String saunaSerial) {
        this.saunaSerial = saunaSerial;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
