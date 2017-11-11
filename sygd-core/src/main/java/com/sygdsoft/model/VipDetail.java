package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-05-09.
 * 没注销的卡记录
 */
public class VipDetail extends BaseEntity {
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
    private Double remain;//余额

    public VipDetail() {
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

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }
}
