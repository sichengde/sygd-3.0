package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 * 团队离店结算具体房号表
 */
public class CheckOutRoom extends BaseEntity{
    private String checkOutSerial;//离店结算序列号
    private String roomId;//房号
    private String selfAccount;//自付帐号
    private String groupAccount;//自付帐号
    private String source;//客源
    private String roomPriceCategory;//房租类型
    private String protocol;//协议价格
    private Date reachTime;//协议价格
    private Date leaveTime;//协议价格
    private String name;//协议价格
    private String company;//协议价格
    private String finalRoomPrice;//协议价格

    public CheckOutRoom() {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFinalRoomPrice() {
        return finalRoomPrice;
    }

    public void setFinalRoomPrice(String finalRoomPrice) {
        this.finalRoomPrice = finalRoomPrice;
    }
}
