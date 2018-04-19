package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-09-20.
 * 报表查询用到的实体类，一般包括用户名，日期区间，销售点等等
 */
public class ReportJson {
    private String userId;
    private Date beginTime;
    private Date endTime;
    private String pointOfSale;
    private String param1;
    private String currency;
    private Boolean paramBool;
    private String format;//pdf还是excel
    private Integer reportIndex;//当这个json作为数组第一个数据的返回值时，这个字段标注了想重打时的索引

    public ReportJson() {
    }

    public ReportJson(String userId, Date beginTime, Date endTime) {
        this.userId = userId;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public boolean getNotNullParamBool(){
        if(paramBool==null){
            return false;
        }else {
            return paramBool;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getReportIndex() {
        return reportIndex;
    }

    public void setReportIndex(Integer reportIndex) {
        this.reportIndex = reportIndex;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public Boolean getParamBool() {
        return paramBool;
    }

    public void setParamBool(Boolean paramBool) {
        this.paramBool = paramBool;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
