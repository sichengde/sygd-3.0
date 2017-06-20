package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 舒展 on 2017-02-07.
 * 出租率=rent/total
 * 平均房价=(allDayRoomConsume+nightRoomConsume)/(allDayRoom+nightRoom)
 * REVPAR=(allDayRoomConsume+nightRoomConsume)/totalReal
 */
public class RoomStateReport extends BaseEntity {
    private String category;//房类
    private Integer total;//总数=空房+维修+自用+备用+出租
    private Integer totalReal;//参与统计的客房（ifRoom为真）
    private Integer empty;//空房
    private Integer repair;//维修
    private Integer self;//自用
    private Integer backUp;//备用
    private Integer rent;//出租=所有过夜审时在住的房（主要用于算出租率）
    private Integer allDayRoom;//全日房=rent里边除去小时房（几率很小）
    private Integer hourRoom;//小时房(夜审周期内小时房开房次数)
    private Integer addRoom;//加收房(夜审周期内加收房租)
    private Integer nightRoom;//凌晨房
    private Double allDayRoomConsume;//全日房房费
    private Double hourRoomConsume;//小时房房费
    private Double addRoomConsume;//加收房房费
    private Double nightRoomConsume;//凌晨房房费
    private Date reportTime;//报表日期
    @Transient
    private Double avaPrice;//平均房价
    @Transient
    private Double rentRate;//出租率

    public RoomStateReport() {
    }

    public RoomStateReport(String category, Integer total, Integer totalReal, Integer empty, Integer repair, Integer self, Integer backUp, Integer rent, Integer allDayRoom, Integer hourRoom, Integer addRoom, Integer nightRoom, Double allDayRoomConsume, Double hourRoomConsume, Double addRoomConsume, Double nightRoomConsume, Date reportTime) {
        this.category = category;
        this.total = total;
        this.totalReal = totalReal;
        this.empty = empty;
        this.repair = repair;
        this.self = self;
        this.backUp = backUp;
        this.rent = rent;
        this.allDayRoom = allDayRoom;
        this.hourRoom = hourRoom;
        this.addRoom = addRoom;
        this.nightRoom = nightRoom;
        this.allDayRoomConsume = allDayRoomConsume;
        this.hourRoomConsume = hourRoomConsume;
        this.addRoomConsume = addRoomConsume;
        this.nightRoomConsume = nightRoomConsume;
        this.reportTime = reportTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getEmpty() {
        return empty;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }

    public Integer getRepair() {
        return repair;
    }

    public void setRepair(Integer repair) {
        this.repair = repair;
    }

    public Integer getSelf() {
        return self;
    }

    public void setSelf(Integer self) {
        this.self = self;
    }

    public Integer getBackUp() {
        return backUp;
    }

    public void setBackUp(Integer backUp) {
        this.backUp = backUp;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }


    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getTotalReal() {
        return totalReal;
    }

    public void setTotalReal(Integer totalReal) {
        this.totalReal = totalReal;
    }

    public Integer getAllDayRoom() {
        return allDayRoom;
    }

    public void setAllDayRoom(Integer allDayRoom) {
        this.allDayRoom = allDayRoom;
    }

    public Integer getHourRoom() {
        return hourRoom;
    }

    public void setHourRoom(Integer hourRoom) {
        this.hourRoom = hourRoom;
    }

    public Integer getAddRoom() {
        return addRoom;
    }

    public void setAddRoom(Integer addRoom) {
        this.addRoom = addRoom;
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

    public Integer getNightRoom() {
        return nightRoom;
    }

    public void setNightRoom(Integer nightRoom) {
        this.nightRoom = nightRoom;
    }

    public Double getNightRoomConsume() {
        return nightRoomConsume;
    }

    public void setNightRoomConsume(Double nightRoomConsume) {
        this.nightRoomConsume = nightRoomConsume;
    }

    public Double getAvaPrice() {
        return avaPrice;
    }

    public void setAvaPrice(Double avaPrice) {
        this.avaPrice = avaPrice;
    }

    public Double getRentRate() {
        return rentRate;
    }

    public void setRentRate(Double rentRate) {
        this.rentRate = rentRate;
    }
}
