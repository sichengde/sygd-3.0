package com.sygdsoft.model;

import java.util.Date;

public class RoomSnapshot extends BaseEntity {
    private String roomId;
    private Date reportTime;
    private String category;
    private String area;
    private String state;
    private String company;
    private String selfAccount;
    private String groupAccount;
    private Boolean realRoom;
    private Boolean empty;
    private Boolean repair;
    private Boolean self;
    private Boolean backUp;
    private Boolean rent;
    private Boolean allDayRoom;
    private Boolean hourRoom;
    private Boolean addRoom;
    private Boolean nightRoom;
    private Double allDayRoomConsume;
    private Double hourRoomConsume;
    private Double addRoomConsume;
    private Double nightRoomConsume;

    public RoomSnapshot() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Boolean getRealRoom() {
        return realRoom;
    }

    public void setRealRoom(Boolean realRoom) {
        this.realRoom = realRoom;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public Boolean getRepair() {
        return repair;
    }

    public void setRepair(Boolean repair) {
        this.repair = repair;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public Boolean getBackUp() {
        return backUp;
    }

    public void setBackUp(Boolean backUp) {
        this.backUp = backUp;
    }

    public Boolean getRent() {
        return rent;
    }

    public void setRent(Boolean rent) {
        this.rent = rent;
    }

    public Boolean getAllDayRoom() {
        return allDayRoom;
    }

    public void setAllDayRoom(Boolean allDayRoom) {
        this.allDayRoom = allDayRoom;
    }

    public Boolean getHourRoom() {
        return hourRoom;
    }

    public void setHourRoom(Boolean hourRoom) {
        this.hourRoom = hourRoom;
    }

    public Boolean getAddRoom() {
        return addRoom;
    }

    public void setAddRoom(Boolean addRoom) {
        this.addRoom = addRoom;
    }

    public Boolean getNightRoom() {
        return nightRoom;
    }

    public void setNightRoom(Boolean nightRoom) {
        this.nightRoom = nightRoom;
    }

    public Double getAllDayRoomConsume() {
        return allDayRoomConsume;
    }

    public void setAllDayRoomConsume(Double allDayRoomConsume) {
        this.allDayRoomConsume = allDayRoomConsume;
    }

    public Double getHourRoomConsume() {
        return hourRoomConsume;
    }

    public void setHourRoomConsume(Double hourRoomConsume) {
        this.hourRoomConsume = hourRoomConsume;
    }

    public Double getAddRoomConsume() {
        return addRoomConsume;
    }

    public void setAddRoomConsume(Double addRoomConsume) {
        this.addRoomConsume = addRoomConsume;
    }

    public Double getNightRoomConsume() {
        return nightRoomConsume;
    }

    public void setNightRoomConsume(Double nightRoomConsume) {
        this.nightRoomConsume = nightRoomConsume;
    }
}
