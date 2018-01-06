package com.sygdsoft.model;

import java.util.Date;

/**
 * 单位储值功能
 */
public class CompanyMoney extends BaseEntity{
    private String company;
    private String category;
    private Double money;
    private String currency;
    private Date doTime;
    private String userId;
    private String remark;
    private String companyPaySerial;

    public CompanyMoney() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyPaySerial() {
        return companyPaySerial;
    }

    public void setCompanyPaySerial(String companyPaySerial) {
        this.companyPaySerial = companyPaySerial;
    }
}
