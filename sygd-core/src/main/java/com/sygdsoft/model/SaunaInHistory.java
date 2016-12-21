package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaInHistory extends BaseEntity{
    private String saunaSerial;
    private String ring;
    private Date doTime;
    private Date doneTime;
    private Double totalPrice;
    private Double finalPrice;//结算价格，为以后计算折扣预留
    private Double discount;//优惠价格，为以后计算折扣预留
    private String userId;
    private Boolean disabled;//该账单作废，自助餐是被冲减，正常桌台是被叫回
    private String inCategory;//账单类型

    public SaunaInHistory() {
    }

    public SaunaInHistory(SaunaIn saunaIn) {
        this.ring = saunaIn.getRing();
        this.doTime = saunaIn.getDoTime();
        this.totalPrice = saunaIn.getTotalPrice();
        this.userId = saunaIn.getUserId();
        this.inCategory = saunaIn.getInCategory();
    }

    public String getSaunaSerial() {
        return saunaSerial;
    }

    public void setSaunaSerial(String saunaSerial) {
        this.saunaSerial = saunaSerial;
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

    public String getInCategory() {
        return inCategory;
    }

    public void setInCategory(String inCategory) {
        this.inCategory = inCategory;
    }
}
