package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-08-24.
 * 订金明细表，餐饮和接待同用一个表
 */
public class BookMoney extends BaseEntity{
    private Double subscription;//订金
    private String bookSerial;//预订单号
    private String userId;//操作员号
    private String currency;//币种
    private Date doTime;//操作时间

    public BookMoney() {
    }

    public Double getSubscription() {
        return subscription;
    }

    public void setSubscription(Double subscription) {
        this.subscription = subscription;
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public void setBookSerial(String bookSerial) {
        this.bookSerial = bookSerial;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }
}
