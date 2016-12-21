package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-09-29.
 */
public class CleanRoom extends BaseEntity{
    private Integer num;
    private String category;
    private String room;
    private String userId;
    private Date doTime;

    public CleanRoom() {
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }
}
