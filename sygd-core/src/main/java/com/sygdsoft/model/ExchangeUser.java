package com.sygdsoft.model;

import javax.persistence.Transient;

/**
 * Created by 舒展 on 2016-06-27.
 * 交班班次设定
 */
public class ExchangeUser extends BaseEntity {
    private String className;//班次名称
    private String beginTime;
    private String endTime;
    @Transient
    private String beginT;//Time会在前端被解释为日期类型
    @Transient
    private String endT;

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

    public String getBeginT() {
        return beginTime;
    }

    public void setBeginT(String beginT) {
        this.beginT = beginT;
    }

    public String getEndT() {
        return endTime;
    }

    public void setEndT(String endT) {
        this.endT = endT;
    }

    public String getRealBeginT(){
        return beginT;
    }

    public String getRealEndT(){
        return endT;
    }
}
