package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-06-27.
 * 交班班次设定
 */
public class ExchangeUser extends BaseEntity {
    private String className;//班次名称
    private String beginTime;
    private String endTime;

    public ExchangeUser() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
