package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-09-20.
 */
public class ExchangeUserCk {
    private String currency;
    private String payMoney;
    private String bookMoney;
    private String cancelBookMoney;
    private String vipMoney;
    private String vipDeserve;
    private ReportJson reportJson;

    public ExchangeUserCk() {
    }

    public ExchangeUserCk(FieldTemplate fieldTemplate) {
        this.currency=fieldTemplate.getField1();
        this.payMoney=fieldTemplate.getField2();
        this.bookMoney=fieldTemplate.getField5();
        this.cancelBookMoney=fieldTemplate.getField6();
        this.vipMoney=fieldTemplate.getField7();
        this.vipDeserve=fieldTemplate.getField8();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getBookMoney() {
        return bookMoney;
    }

    public void setBookMoney(String bookMoney) {
        this.bookMoney = bookMoney;
    }

    public String getCancelBookMoney() {
        return cancelBookMoney;
    }

    public void setCancelBookMoney(String cancelBookMoney) {
        this.cancelBookMoney = cancelBookMoney;
    }

    public String getVipMoney() {
        return vipMoney;
    }

    public void setVipMoney(String vipMoney) {
        this.vipMoney = vipMoney;
    }

    public ReportJson getReportJson() {
        return reportJson;
    }

    public void setReportJson(ReportJson reportJson) {
        this.reportJson = reportJson;
    }

    public String getVipDeserve() {
        return vipDeserve;
    }

    public void setVipDeserve(String vipDeserve) {
        this.vipDeserve = vipDeserve;
    }
}
