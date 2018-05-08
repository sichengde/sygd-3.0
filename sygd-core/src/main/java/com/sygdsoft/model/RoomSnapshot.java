package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;

public class RoomSnapshot extends BaseEntity {
    private String roomId;
    private Date reportTime;
    private String category;
    private String area;
    private String state;
    private String company;
    private String guestSource;
    private String selfAccount;
    private String groupAccount;
    private Boolean realRoom;//属于房间
    private Boolean empty;//空房
    private Boolean repair;//维修房
    private Boolean self;//自用房
    private Boolean backUp;//备用房
    private Boolean rent;//出租房
    private Boolean available;//可用房
    private Boolean free;//招待房/免费房
    private Boolean allDayRoom;
    private Boolean hourRoom;
    private Boolean addRoom;
    private Boolean nightRoom;
    private Double allDayRoomConsume;
    private Double hourRoomConsume;
    private Double addRoomConsume;
    private Double nightRoomConsume;
    @Transient
    private Integer sumRealRoom;
    @Transient
    private Integer sumEmpty;
    @Transient
    private Integer sumRepair;
    @Transient
    private Integer sumSelf;
    @Transient
    private Integer sumBackUp;
    @Transient
    private Integer sumRent;
    @Transient
    private Integer sumFree;
    @Transient
    private Integer sumAllDayRoom;
    @Transient
    private Integer sumHourRoom;
    @Transient
    private Integer sumAddRoom;
    @Transient
    private Integer sumNightRoom;
    @Transient
    private Integer sumAvailable;
    @Transient
    private String currency;
    @Transient
    private String countCategory;

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

    public Integer getSumRealRoom() {
        return sumRealRoom;
    }

    public void setSumRealRoom(Integer sumRealRoom) {
        this.sumRealRoom = sumRealRoom;
    }

    public Integer getSumEmpty() {
        return sumEmpty;
    }

    public void setSumEmpty(Integer sumEmpty) {
        this.sumEmpty = sumEmpty;
    }

    public Integer getSumRepair() {
        return sumRepair;
    }

    public void setSumRepair(Integer sumRepair) {
        this.sumRepair = sumRepair;
    }

    public Integer getSumSelf() {
        return sumSelf;
    }

    public void setSumSelf(Integer sumSelf) {
        this.sumSelf = sumSelf;
    }

    public Integer getSumBackUp() {
        return sumBackUp;
    }

    public void setSumBackUp(Integer sumBackUp) {
        this.sumBackUp = sumBackUp;
    }

    public Integer getSumRent() {
        return sumRent;
    }

    public void setSumRent(Integer sumRent) {
        this.sumRent = sumRent;
    }

    public Integer getSumAllDayRoom() {
        return sumAllDayRoom;
    }

    public void setSumAllDayRoom(Integer sumAllDayRoom) {
        this.sumAllDayRoom = sumAllDayRoom;
    }

    public Integer getSumHourRoom() {
        return sumHourRoom;
    }

    public void setSumHourRoom(Integer sumHourRoom) {
        this.sumHourRoom = sumHourRoom;
    }

    public Integer getSumAddRoom() {
        return sumAddRoom;
    }

    public void setSumAddRoom(Integer sumAddRoom) {
        this.sumAddRoom = sumAddRoom;
    }

    public Integer getSumNightRoom() {
        return sumNightRoom;
    }

    public void setSumNightRoom(Integer sumNightRoom) {
        this.sumNightRoom = sumNightRoom;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getSumAvailable() {
        return sumAvailable;
    }

    public void setSumAvailable(Integer sumAvailable) {
        this.sumAvailable = sumAvailable;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Integer getSumFree() {
        return sumFree;
    }

    public void setSumFree(Integer sumFree) {
        this.sumFree = sumFree;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getCountCategory() {
        return countCategory;
    }

    public void setCountCategory(String countCategory) {
        this.countCategory = countCategory;
    }
}
