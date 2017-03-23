package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-27.
 */
public class RoomPriceAdd extends BaseEntity {
    private String beginTime;//起始时间
    private String endTime;//终止时间
    private String tiLimit;//时限
    private String roomAddByMultiple;//房租折扣
    private String roomAddStatic;//房租固定
    private Boolean vip;//会员类别

    public RoomPriceAdd() {
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTiLimit() {
        return tiLimit;
    }

    public void setTiLimit(String tiLimit) {
        this.tiLimit = tiLimit;
    }

    public String getRoomAddByMultiple() {
        return roomAddByMultiple;
    }

    public void setRoomAddByMultiple(String roomAddByMultiple) {
        this.roomAddByMultiple = roomAddByMultiple;
    }

    public String getRoomAddStatic() {
        return roomAddStatic;
    }

    public void setRoomAddStatic(String roomAddStatic) {
        this.roomAddStatic = roomAddStatic;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
}
