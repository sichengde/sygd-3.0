package com.sygdsoft.model;

import java.util.Date;

public class CheckOutPayBack extends BaseEntity{
    private Date doneTime;
    private String currency;
    private Double money;
    private String checkOutSerial;
    private String userId;

    public CheckOutPayBack() {
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
