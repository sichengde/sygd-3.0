package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-01-12.
 */
public class InterfaceDoor extends BaseEntity{
    private String roomId;//房间号码
    private String doorId;//门锁号码
    private String telId;//分机号码

    public InterfaceDoor() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getTelId() {
        return telId;
    }

    public void setTelId(String telId) {
        this.telId = telId;
    }
}
