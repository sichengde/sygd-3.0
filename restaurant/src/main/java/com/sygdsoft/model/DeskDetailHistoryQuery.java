package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class DeskDetailHistoryQuery {
    private String firstPointOfSale;
    private String secondPointOfSale;
    private Date beginTime;
    private Date endTime;

    public DeskDetailHistoryQuery() {
    }

    public String getFirstPointOfSale() {
        return firstPointOfSale;
    }

    public void setFirstPointOfSale(String firstPointOfSale) {
        this.firstPointOfSale = firstPointOfSale;
    }

    public String getSecondPointOfSale() {
        return secondPointOfSale;
    }

    public void setSecondPointOfSale(String secondPointOfSale) {
        this.secondPointOfSale = secondPointOfSale;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
