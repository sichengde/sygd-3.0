package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2016-09-07.
 */
public class DebtIntegration {
    private String paySerial;//结账序列号
    private Date doTime;//操作日期
    private String pointOfSale;//销售点
    private Double consume;//消费
    private Double deposit;//押金
    private String currency;//币种
    private String description;//描述
    private String selfAccount;//自付帐号
    private String groupAccount;//公付帐号
    private String roomId;//房号
    private String protocol;//房租方式
    private Date doneTime;//结账日期
    private String userId;//操作员号
    private String bed;//床位号，针对查询客人消费
    private String vipNumber;//会员编号，针对于用会员余额作为押金的顾客
    private String category;//账务类别，便于在账务明细中筛选
    private String guestSource;//客源
    private String company;//单位
    @Transient
    private String count;
    @Transient
    private String categoryRoom;

    public DebtIntegration() {
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

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
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

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCategoryRoom() {
        return categoryRoom;
    }

    public void setCategoryRoom(String categoryRoom) {
        this.categoryRoom = categoryRoom;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
