package com.sygdsoft.model;

import com.sygdsoft.util.SzMath;

/**
 * Created by 舒展 on 2016-09-28.
 */
public class ExchangeUserRow {
    private String currency;
    private Double pay;
    private Double deposit;
    private Double cancelDeposit;
    private Double cancelDepositSingle;
    private Double subscription;
    private Double cancelSubscription;
    private Double vipRecharge;
    private Double companyPay;
    private Double payBack;//结账召回的币种信息
    private Double depositAll;//在店预付币种

    public ExchangeUserRow() {
    }
    public ExchangeUserRow(FieldTemplate fieldTemplate) {
        SzMath szMath=new SzMath();
        this.currency=fieldTemplate.getField1();
        this.pay=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField2());
        this.deposit=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField3());
        this.cancelDeposit=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField4());
        this.cancelDepositSingle=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField5());
        this.subscription=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField6());
        this.cancelSubscription=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField7());
        this.vipRecharge=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField8());
        this.companyPay=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField9());//暂时作废
        this.payBack=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField10());
        this.depositAll=szMath.formatTwoDecimalReturnDouble(fieldTemplate.getField11());
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getCancelDeposit() {
        return cancelDeposit;
    }

    public void setCancelDeposit(Double cancelDeposit) {
        this.cancelDeposit = cancelDeposit;
    }

    public Double getCancelDepositSingle() {
        return cancelDepositSingle;
    }

    public void setCancelDepositSingle(Double cancelDepositSingle) {
        this.cancelDepositSingle = cancelDepositSingle;
    }

    public Double getSubscription() {
        return subscription;
    }

    public void setSubscription(Double subscription) {
        this.subscription = subscription;
    }

    public Double getCancelSubscription() {
        return cancelSubscription;
    }

    public void setCancelSubscription(Double cancelSubscription) {
        this.cancelSubscription = cancelSubscription;
    }

    public Double getVipRecharge() {
        return vipRecharge;
    }

    public void setVipRecharge(Double vipRecharge) {
        this.vipRecharge = vipRecharge;
    }

    public Double getCompanyPay() {
        return companyPay;
    }

    public void setCompanyPay(Double companyPay) {
        this.companyPay = companyPay;
    }

    public Double getPayBack() {
        return payBack;
    }

    public void setPayBack(Double payBack) {
        this.payBack = payBack;
    }

    public Double getDepositAll() {
        return depositAll;
    }

    public void setDepositAll(Double depositAll) {
        this.depositAll = depositAll;
    }
}
