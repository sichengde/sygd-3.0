package com.sygdsoft.model;

import javax.persistence.Transient;

/**
 * Created by 舒展 on 2016-03-30.
 * 房间类别实体
 */
public class RoomCategory extends BaseEntity {
    private String category;
    private String cloudPic;
    private Boolean wifi;
    private Boolean computer;
    private Boolean hairDrier;
    private Boolean clock;
    @Transient
    private Integer remain;//剩余数量
    @Transient
    private Integer live;//在住数量
    @Transient
    private Integer todayLeave;//预离数量

    public RoomCategory() {
    }

    public int getNotNullRemain(){
        if(remain==null){
            return 0;
        }else {
            return remain;
        }
    }

    public int getNotNullLive(){
        if(live==null){
            return 0;
        }else {
            return live;
        }
    }

    public int getNotNullTodayLeave(){
        if(todayLeave==null){
            return 0;
        }else {
            return todayLeave;
        }
    }

    public RoomCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCloudPic() {
        return cloudPic;
    }

    public void setCloudPic(String cloudPic) {
        this.cloudPic = cloudPic;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getComputer() {
        return computer;
    }

    public void setComputer(Boolean computer) {
        this.computer = computer;
    }

    public Boolean getHairDrier() {
        return hairDrier;
    }

    public void setHairDrier(Boolean hairDrier) {
        this.hairDrier = hairDrier;
    }

    public Boolean getClock() {
        return clock;
    }

    public void setClock(Boolean clock) {
        this.clock = clock;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getTodayLeave() {
        return todayLeave;
    }

    public void setTodayLeave(Integer todayLeave) {
        this.todayLeave = todayLeave;
    }

    public Integer getLive() {
        return live;
    }

    public void setLive(Integer live) {
        this.live = live;
    }
}
