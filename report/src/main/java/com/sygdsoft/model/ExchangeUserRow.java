package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-09-28.
 */
public class ExchangeUserRow {
    private String currency;
    private String pay;
    private String deposit;
    private String cancelDeposit;
    private String subscription;
    private String cancelSubscription;
    private String vipRecharge;
    private String companyPay;

    public ExchangeUserRow() {
    }
    public ExchangeUserRow(FieldTemplate fieldTemplate) {
        this.currency=fieldTemplate.getField1();
        this.pay=fieldTemplate.getField2();
        this.deposit=fieldTemplate.getField3();
        this.cancelDeposit=fieldTemplate.getField4();
        this.subscription=fieldTemplate.getField5();
        this.cancelSubscription=fieldTemplate.getField6();
        this.vipRecharge=fieldTemplate.getField7();
        this.companyPay=fieldTemplate.getField8();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getCancelDeposit() {
        return cancelDeposit;
    }

    public void setCancelDeposit(String cancelDeposit) {
        this.cancelDeposit = cancelDeposit;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getCancelSubscription() {
        return cancelSubscription;
    }

    public void setCancelSubscription(String cancelSubscription) {
        this.cancelSubscription = cancelSubscription;
    }

    public String getVipRecharge() {
        return vipRecharge;
    }

    public void setVipRecharge(String vipRecharge) {
        this.vipRecharge = vipRecharge;
    }

    public String getCompanyPay() {
        return companyPay;
    }

    public void setCompanyPay(String companyPay) {
        this.companyPay = companyPay;
    }
}
