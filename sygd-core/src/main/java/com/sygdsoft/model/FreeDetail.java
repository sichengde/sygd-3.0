package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-07-26.
 */
public class FreeDetail {
    private String freeman;//宴请签单人
    private String paySerial;//结账序列号
    private String pointOfSale;//营业部门
    private Double consume;//宴请金额
    private Date doTime;//操作时间
    private String userId;//操作员号
    private String reason;//宴请原因

    public FreeDetail() {
    }

    public String getFreeman() {
        return freeman;
    }

    public void setFreeman(String freeman) {
        this.freeman = freeman;
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
