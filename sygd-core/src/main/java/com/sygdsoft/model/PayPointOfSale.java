package com.sygdsoft.model;

import java.util.Date;

public class PayPointOfSale extends BaseEntity {
    private Integer debtPayId;
    private Integer companyPayId;
    private String pointOfSale;
    private String currency;
    private Double money;
    private Date doTime;

    public PayPointOfSale() {
    }

    public PayPointOfSale(PayPointOfSale payPointOfSale) {
        this.debtPayId = payPointOfSale.getDebtPayId();
        this.companyPayId = payPointOfSale.getCompanyPayId();
        this.pointOfSale = payPointOfSale.getPointOfSale();
        this.money = payPointOfSale.getMoney();
        this.doTime = payPointOfSale.getDoTime();
    }

    public Integer getDebtPayId() {
        return debtPayId;
    }

    public void setDebtPayId(Integer debtPayId) {
        this.debtPayId = debtPayId;
    }

    public Integer getCompanyPayId() {
        return companyPayId;
    }

    public void setCompanyPayId(Integer companyPayId) {
        this.companyPayId = companyPayId;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
