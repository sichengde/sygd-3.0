package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-07.
 */
public class RoomStateReport extends BaseEntity {
    private String category;//房类
    private Integer total;//总数
    private Integer empty;//空房
    private Integer repair;//维修
    private Integer self;//自用
    private Integer backUp;//备用
    private Integer rent;//出租
    private Date reportTime;//报表日期

    public RoomStateReport() {
    }

    public RoomStateReport(String category, Integer total, Integer empty, Integer repair, Integer self, Integer backUp, Integer rent, Date reportTime) {
        this.category = category;
        this.total = total;
        this.empty = empty;
        this.repair = repair;
        this.self = self;
        this.backUp = backUp;
        this.rent = rent;
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
}
