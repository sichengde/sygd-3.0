package com.sygdsoft.jsonModel;

import java.util.Date;

/**
 * Created by 舒展 on 2016-11-25.
 * 房类统计线性表格
 */
public class RoomCategoryLine {
    private String categoryRoom;//房间类别
    private Double average;//平均房价
    private Date date;//日期

    public RoomCategoryLine() {
    }

    public String getCategoryRoom() {
        return categoryRoom;
    }

    public void setCategoryRoom(String categoryRoom) {
        this.categoryRoom = categoryRoom;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
