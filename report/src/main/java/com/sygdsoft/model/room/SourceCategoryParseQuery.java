package com.sygdsoft.model.room;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-06-12.
 */
public class SourceCategoryParseQuery {
    private String mode;
    private Date beginTime;
    private Date endTime;
    private List<String> guestSourceList;
    private List<String> roomCategoryList;
    private List<String> saleCountList;
    private List<String> pointOfSaleList;

    public SourceCategoryParseQuery() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getGuestSourceList() {
        return guestSourceList;
    }

    public void setGuestSourceList(List<String> guestSourceList) {
        this.guestSourceList = guestSourceList;
    }

    public List<String> getRoomCategoryList() {
        return roomCategoryList;
    }

    public void setRoomCategoryList(List<String> roomCategoryList) {
        this.roomCategoryList = roomCategoryList;
    }

    public List<String> getSaleCountList() {
        return saleCountList;
    }

    public void setSaleCountList(List<String> saleCountList) {
        this.saleCountList = saleCountList;
    }

    public List<String> getPointOfSaleList() {
        return pointOfSaleList;
    }

    public void setPointOfSaleList(List<String> pointOfSaleList) {
        this.pointOfSaleList = pointOfSaleList;
    }
}
