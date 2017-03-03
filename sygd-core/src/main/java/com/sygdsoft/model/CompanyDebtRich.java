package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-03-03.
 */
public class CompanyDebtRich {
    private String company;
    private String lord;
    private String paySerial;
    private Double debt;
    private Date companyDoTime;
    private String description;
    private String pointOfSale;
    private Date debtDoTime;
    private String secondPointOfSale;
    private String selfAccount;
    private String groupAccount;
    private String roomId;
    private String userId;
    private String category;
    private String remark;
    private Boolean companyPaid;
    private String name;

    public CompanyDebtRich() {
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

    public Date getCompanyDoTime() {
        return companyDoTime;
    }

    public void setCompanyDoTime(Date companyDoTime) {
        this.companyDoTime = companyDoTime;
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

    public Date getDebtDoTime() {
        return debtDoTime;
    }

    public void setDebtDoTime(Date debtDoTime) {
        this.debtDoTime = debtDoTime;
    }

    public String getSecondPointOfSale() {
        return secondPointOfSale;
    }

    public void setSecondPointOfSale(String secondPointOfSale) {
        this.secondPointOfSale = secondPointOfSale;
    }

    public String getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(String selfAccount) {
        this.selfAccount = selfAccount;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getCompanyPaid() {
        return companyPaid;
    }

    public void setCompanyPaid(Boolean companyPaid) {
        this.companyPaid = companyPaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
