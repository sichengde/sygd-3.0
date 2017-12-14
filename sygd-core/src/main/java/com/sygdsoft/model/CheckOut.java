package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 * 离店明细
 */
public class CheckOut extends BaseEntity{
    private String checkOutSerial;//离店序列号
    private String roomId;//房号
    private Date checkOutTime;//离店时间
    private Date reachTime;//离店时间
    private String selfAccount;//公付帐号
    private String groupAccount;//公付帐号
    private Double consume;//消费合计
    private Double deposit;//消费合计
    private String userId;//操作员号
    private String remark;//备注
    private String company;//单位
    private String groupName;//单位
    private Double fpMoney;//手写发票金额，仅用于打印结账单和补打
    public CheckOut() {
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
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

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getFpMoney() {
        return fpMoney;
    }

    public void setFpMoney(Double fpMoney) {
        this.fpMoney = fpMoney;
    }
}
