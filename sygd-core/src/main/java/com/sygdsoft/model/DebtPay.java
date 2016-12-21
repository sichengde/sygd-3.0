package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 * 结账记录
 */
public class DebtPay extends BaseEntity {
    private String paySerial;//结账序列号
    private String checkOutSerial;//离店结算序列号
    private Double debtMoney;//结帐金额
    private String currency;//币种
    private String currencyAdd;//币种额外信息
    private Date doneTime;//结账时间
    private String debtCategory;//结账类型
    private String company;//开房单位
    private String description;//描述
    private String pointOfSale;//销售点
    private String selfAccount;//自付帐号
    private String groupAccount;//公付帐号
    private String vipNumber;//会员卡号
    private String userId;//操作员
    private String roomId;//房间列表，没有就是空

    public DebtPay() {
    }

    public DebtPay(DebtPay debtPay) {
        this.paySerial = debtPay.getPaySerial();
        this.checkOutSerial = debtPay.getCheckOutSerial();
        this.debtMoney = debtPay.getDebtMoney();
        this.currency = debtPay.getCurrency();
        this.currencyAdd = debtPay.getCurrencyAdd();
        this.doneTime = debtPay.getDoneTime();
        this.debtCategory = debtPay.getDebtCategory();
        this.company = debtPay.getCompany();
        this.description = debtPay.getDescription();
        this.pointOfSale = debtPay.getPointOfSale();
        this.selfAccount = debtPay.getSelfAccount();
        this.groupAccount = debtPay.getGroupAccount();
        this.vipNumber = debtPay.getVipNumber();
        this.userId = debtPay.getUserId();
        this.roomId = debtPay.getRoomId();
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }

    public Double getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(Double debtMoney) {
        this.debtMoney = debtMoney;
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

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getDebtCategory() {
        return debtCategory;
    }

    public void setDebtCategory(String debtCategory) {
        this.debtCategory = debtCategory;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
