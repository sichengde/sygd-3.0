package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class DeskProfitQuery {
    private Date beginTime ;//起始时间
    private Date endTime ;//终止时间
    private String pointOfSale ;//营业部门
    private String category ;//品种类别

    public DeskProfitQuery() {
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

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
