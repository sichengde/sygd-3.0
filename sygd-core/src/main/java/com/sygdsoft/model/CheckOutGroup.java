package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-09-12.
 */
public class CheckOutGroup extends BaseEntity{
    private String groupAccount;
    private String name;
    private Date reachTime;
    private Date leaveTime;
    private String leader;
    private String leaderRoom;
    private String phone;
    private String vipNumber;
    private String currency;
    private Double deposit;
    private Double consume;
    private Double pay;
    private String company;
    private String lord;
    private String userId;
    private String guestSource;
    private String roomPriceCategory;
    private String protocol;
    private Integer totalRoom;
    private String remark;
    private String checkOutSerial;

    public CheckOutGroup() {
    }
    public CheckOutGroup(CheckInGroup checkInGroup){
        this.groupAccount=checkInGroup.getGroupAccount();
        this.name=checkInGroup.getName();
        this.reachTime=checkInGroup.getReachTime();
        this.leaveTime=checkInGroup.getLeaveTime();
        this.leader=checkInGroup.getLeader();
        this.leaderRoom=checkInGroup.getLeaderRoom();
        this.phone=checkInGroup.getPhone();
        this.vipNumber=checkInGroup.getVipNumber();
        this.currency=checkInGroup.getCurrency();
        this.deposit=checkInGroup.getDeposit();
        this.consume=checkInGroup.getConsume();
        this.pay=checkInGroup.getPay();
        this.company=checkInGroup.getCompany();
        this.lord=checkInGroup.getLord();
        this.userId=checkInGroup.getUserId();
        this.guestSource=checkInGroup.getGuestSource();
        this.roomPriceCategory=checkInGroup.getRoomPriceCategory();
        this.protocol=checkInGroup.getProtocol();
        this.totalRoom=checkInGroup.getTotalRoom();
        this.remark=checkInGroup.getRemark();
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderRoom() {
        return leaderRoom;
    }

    public void setLeaderRoom(String leaderRoom) {
        this.leaderRoom = leaderRoom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLord() {
        return lord;
    }

    public void setLord(String lord) {
        this.lord = lord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
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

    public Integer getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Integer totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckOutSerial() {
        return checkOutSerial;
    }

    public void setCheckOutSerial(String checkOutSerial) {
        this.checkOutSerial = checkOutSerial;
    }
}
