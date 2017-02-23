package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-23.
 */
public class CompanyPay extends BaseEntity{
    public String companyPaySerial;//单位结账序列号
    public Double debt;//结挂账款
    public Double pay;//实收金额
    public String currency;//币种
    public String currencyAdd;//币种额外信息
    public String remark;//币种额外信息
    public Date doneTime;//结账时间

    public CompanyPay() {
    }

    public String getCompanyPaySerial() {
        return companyPaySerial;
    }

    public void setCompanyPaySerial(String companyPaySerial) {
        this.companyPaySerial = companyPaySerial;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyAdd() {
        return currencyAdd;
    }

    public void setCurrencyAdd(String currencyAdd) {
        this.currencyAdd = currencyAdd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }
}
