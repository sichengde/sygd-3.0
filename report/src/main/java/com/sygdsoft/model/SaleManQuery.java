package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-01-05.
 */
public class SaleManQuery {
    private String saleMan;
    private Date beginTime;
    private Date endTime;

    public SaleManQuery() {
    }

    public String getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(String saleMan) {
        this.saleMan = saleMan;
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
