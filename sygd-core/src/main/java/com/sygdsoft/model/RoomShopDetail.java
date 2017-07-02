package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2016-09-21.
 * 房吧和商品零售的明细
 */
public class RoomShopDetail {
    private String item;
    private String category;
    private Double price;
    private Double num;
    private Double totalMoney;
    private String room;
    private String selfAccount;//有自付账号说明是房吧录入的
    private String paySerial;//有结账序列号说明是商品零售
    private Date doTime;
    private String userId;//操作员号
    private String unit;//单位
    private Boolean cargo;//是否参与库存统计
    private Boolean storageDone;//是否参与库存统计

    @Transient
    private Date doneTime;//跟debtPay联表查询得出的结账时间

    public RoomShopDetail() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(String selfAccount) {
        this.selfAccount = selfAccount;
    }

    public String getPaySerial() {
        return paySerial;
    }

    public void setPaySerial(String paySerial) {
        this.paySerial = paySerial;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

    public Boolean getStorageDone() {
        return storageDone;
    }

    public void setStorageDone(Boolean storageDone) {
        this.storageDone = storageDone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
