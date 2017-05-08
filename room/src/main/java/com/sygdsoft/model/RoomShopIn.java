package com.sygdsoft.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class RoomShopIn {
    private String roomId;
    private String money;
    private String description;
    private String bed;
    private String guest;
    private List<RoomShopDetail> roomShopDetailList;

    public RoomShopIn() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public List<RoomShopDetail> getRoomShopDetailList() {
        return roomShopDetailList;
    }

    public void setRoomShopDetailList(List<RoomShopDetail> roomShopDetailList) {
        this.roomShopDetailList = roomShopDetailList;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }
}
