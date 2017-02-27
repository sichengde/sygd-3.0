package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-24.
 */
public class CompanyDebtIntegration extends BaseEntity{
    private String company;//单位代码
    private String lord;//签单人代码
    private String paySerial;//结账序列号
    private Double debt;//单位挂账
    private Double currentRemain;//这笔账进行完之后当前的剩余结账款
    private Date doTime;//操作时间
    private String userId;//操作员号
    private String description;//备注，描述
    private String pointOfSale;//营业部门

    public CompanyDebtIntegration() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLord() {
        return lord;
    }

    public void setLord(String lord) {
        this.lord = lord;
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getCurrentRemain() {
        return currentRemain;
    }

    public void setCurrentRemain(Double currentRemain) {
        this.currentRemain = currentRemain;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }
}