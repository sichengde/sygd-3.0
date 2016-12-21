package com.sygdsoft.model;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-29.
 */
public class CleanRoomPost {
    private List<Room> roomList;
    private String userId;

    public CleanRoomPost() {
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
