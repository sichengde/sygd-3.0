package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/27 0027.
 * 因为注销的卡可能有多张是一个卡号，所以需要用fatherId来索引
 */
public class VipDetailHistory extends BaseEntity{
    private Integer fatherId;//父索引
    private String vipNumber;//会员卡号
    private String pointOfSale;//销售点
    private Double consume;//消费
    private String currency;//消费
    private Double pay;//充值
    private Double deserve;//抵用金额（例如冲500抵用600）
    private String category;//支付方式?余额还是现金
    private String description;//描述
    private String selfAccount;//自付帐号
    private String groupAccount;//公付帐号
    private String paySerial;//结账序列号
    private String userId;//操作员号
    private Date doTime;//操作时间

    public VipDetailHistory() {
    }

    public VipDetailHistory(VipDetail vipDetail,Integer fatherId) {
        this.fatherId = fatherId;
        this.vipNumber = vipDetail.getVipNumber();
        this.pointOfSale = vipDetail.getPointOfSale();
        this.consume = vipDetail.getConsume();
        this.currency = vipDetail.getCurrency();
        this.pay = vipDetail.getPay();
        this.deserve = vipDetail.getDeserve();
        this.category = vipDetail.getCategory();
        this.description = vipDetail.getDescription();
        this.selfAccount = vipDetail.getSelfAccount();
        this.groupAccount = vipDetail.getGroupAccount();
        this.paySerial = vipDetail.getPaySerial();
        this.userId = vipDetail.getUserId();
        this.doTime = vipDetail.getDoTime();
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
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

    public Double getDeserve() {
        return deserve;
    }

    public void setDeserve(Double deserve) {
        this.deserve = deserve;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }
}
