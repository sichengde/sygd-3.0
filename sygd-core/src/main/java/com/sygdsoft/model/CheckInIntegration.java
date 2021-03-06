package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-01-05.
 */
public class CheckInIntegration {
    private String roomId;
    private String roomCategory;
    private String roomPriceCategory;
    private String selfAccount;
    private String guestSource;
    private Double finalRoomPrice;
    private Double consume;
    private Date reachTime;
    private Date leaveTime;
    private String remark;
    private String userId;
    private Boolean ifRoom;//
    private Boolean ifIn;//是否在住

    public CheckInIntegration() {
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

    public Double getFinalRoomPrice() {
        return finalRoomPrice;
    }

    public void setFinalRoomPrice(Double finalRoomPrice) {
        this.finalRoomPrice = finalRoomPrice;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(String selfAccount) {
        this.selfAccount = selfAccount;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public Boolean getIfRoom() {
        return ifRoom;
    }

    public void setIfRoom(Boolean ifRoom) {
        this.ifRoom = ifRoom;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIfIn() {
        return ifIn;
    }

    public void setIfIn(Boolean ifIn) {
        this.ifIn = ifIn;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }
}
