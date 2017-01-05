package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-01-05.
 */
public class CheckInIntegration {
    private String roomId;
    private String roomCategory;
    private Double finalRoomPrice;
    private Date reachTime;
    private Date leaveTime;
    private String remark;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
