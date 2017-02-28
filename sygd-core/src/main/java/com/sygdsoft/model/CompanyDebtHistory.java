package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-23.
 */
public class CompanyDebtHistory extends BaseEntity {
    public String company;//
    public String lord;
    public String paySerial;
    public Double debt;
    public Date doTime;
    public Date doneTime;
    public String userId;
    public String description;
    public String pointOfSale;
    public String secondPointOfSale;
    public Double currentRemain;
    public String companyPaySerial;

    public CompanyDebtHistory() {
    }

    public CompanyDebtHistory(CompanyDebt companyDebt) {
        this.company=companyDebt.getCompany();
        this.lord=companyDebt.getLord();
        this.paySerial=companyDebt.getPaySerial();
        this.debt=companyDebt.getDebt();
        this.doTime=companyDebt.getDoTime();
        this.userId=companyDebt.getUserId();
        this.description=companyDebt.getDescription();
        this.pointOfSale=companyDebt.getPointOfSale();
        this.secondPointOfSale=companyDebt.getSecondPointOfSale();
        this.currentRemain=companyDebt.getCurrentRemain();
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

    public Double getCurrentRemain() {
        return currentRemain;
    }

    public void setCurrentRemain(Double currentRemain) {
        this.currentRemain = currentRemain;
    }

    public String getCompanyPaySerial() {
        return companyPaySerial;
    }

    public void setCompanyPaySerial(String companyPaySerial) {
        this.companyPaySerial = companyPaySerial;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getSecondPointOfSale() {
        return secondPointOfSale;
    }

    public void setSecondPointOfSale(String secondPointOfSale) {
        this.secondPointOfSale = secondPointOfSale;
    }
}
