package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-13.
 * 流水号，所有用到的流水号都在这个表里
 */
public class Serial extends BaseEntity{
    private String paySerial;//结账序列号
    private String bookSerial;//预订单号
    private String selfAccount;//自付账号
    private String groupAccount;//公付账号
    private String checkOutSerial;//离店结算序列号
    private String checkOutSerialFp;//离店结算序列号(发票)
    private String ckSerial;//餐饮结算序列号
    private String deskBookSerial;//餐饮预定序列号
    private String storageOutSerial;//库存出库序列号
    private String storageInSerial;//库存入库序列号
    private String saunaGroupSerial;//桑拿结算序列号
    private String saunaOutSerial;//桑拿团队手牌序列号
    private String companyPaySerial;//单位结算序列号

    public Serial() {
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public void setBookSerial(String bookSerial) {
        this.bookSerial = bookSerial;
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

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }

    public String getCkSerial() {
        return ckSerial;
    }

    public void setCkSerial(String ckSerial) {
        this.ckSerial = ckSerial;
    }

    public String getDeskBookSerial() {
        return deskBookSerial;
    }

    public void setDeskBookSerial(String deskBookSerial) {
        this.deskBookSerial = deskBookSerial;
    }

    public String getStorageOutSerial() {
        return storageOutSerial;
    }

    public void setStorageOutSerial(String storageOutSerial) {
        this.storageOutSerial = storageOutSerial;
    }

    public String getStorageInSerial() {
        return storageInSerial;
    }

    public void setStorageInSerial(String storageInSerial) {
        this.storageInSerial = storageInSerial;
    }

    public String getSaunaGroupSerial() {
        return saunaGroupSerial;
    }

    public void setSaunaGroupSerial(String saunaGroupSerial) {
        this.saunaGroupSerial = saunaGroupSerial;
    }

    public String getSaunaOutSerial() {
        return saunaOutSerial;
    }

    public void setSaunaOutSerial(String saunaOutSerial) {
        this.saunaOutSerial = saunaOutSerial;
    }

    public String getCompanyPaySerial() {
        return companyPaySerial;
    }

    public void setCompanyPaySerial(String companyPaySerial) {
        this.companyPaySerial = companyPaySerial;
    }

    public String getCheckOutSerialFp() {
        return checkOutSerialFp;
    }

    public void setCheckOutSerialFp(String checkOutSerialFp) {
        this.checkOutSerialFp = checkOutSerialFp;
    }
}
