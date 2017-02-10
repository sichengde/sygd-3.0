package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-27.
 */
public class CheckInHistoryLog extends BaseEntity {
    private String cardId;//证件号码
    private String roomId;//房号
    private String roomCategory;//房类
    private String selfAccount;//自付帐号
    private String groupAccount;//公付帐号
    private Date reachTime;//来店时间
    private Date leaveTime;//离店时间
    private String guestSource;//客源代码
    private String important;//特殊要求
    private Boolean vip;//贵宾标志
    private String breakfast;//早餐类型
    private String remark;//备注
    private Double finalRoomPrice;//开房房价
    private String protocol;//协议价格编码
    private String company;//协议价格编码
    private String vipNumber;//会员编号
    private Double deposit;//押金
    private Double consume;//消费
    private Double pay;//已经支付的金额（用于中间结算）
    private String roomPriceCategory;//房租方式 hour---小时房，day---日租房
    private String userId;//操作员号
    private String checkOutSerial;//离店结算序列号
    private Boolean ifRoom;//

    public CheckInHistoryLog() {
    }
    /*public CheckInHistoryLog(CheckIn checkIn){
        this.cardId=checkIn.getCardId();
        this.selfAccount=checkIn.getSelfAccount();
        this.roomId=checkIn.getRoomId();
    }*/
    public CheckInHistoryLog(CheckIn checkIn){
        this.roomId=checkIn.getRoomId();
        this.roomCategory=checkIn.getRoomCategory();
        this.selfAccount=checkIn.getSelfAccount();
        this.groupAccount=checkIn.getGroupAccount();
        this.reachTime=checkIn.getReachTime();
        this.leaveTime=checkIn.getLeaveTime();
        this.guestSource=checkIn.getGuestSource();
        this.important=checkIn.getImportant();
        this.vip=checkIn.getVip();
        this.breakfast=checkIn.getBreakfast();
        this.remark=checkIn.getRemark();
        this.finalRoomPrice=checkIn.getFinalRoomPrice();
        this.protocol=checkIn.getProtocol();
        this.company=checkIn.getCompany();
        this.vipNumber=checkIn.getVipNumber();
        this.deposit=checkIn.getDeposit();
        this.consume=checkIn.getConsume();
        this.pay=checkIn.getPay();
        this.roomPriceCategory=checkIn.getRoomPriceCategory();
        this.userId=checkIn.getUserId();
        this.ifRoom=checkIn.getIfRoom();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
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

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getFinalRoomPrice() {
        return finalRoomPrice;
    }

    public void setFinalRoomPrice(Double finalRoomPrice) {
        this.finalRoomPrice = finalRoomPrice;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }

    public Boolean getIfRoom() {
        return ifRoom;
    }

    public void setIfRoom(Boolean ifRoom) {
        this.ifRoom = ifRoom;
    }
}
