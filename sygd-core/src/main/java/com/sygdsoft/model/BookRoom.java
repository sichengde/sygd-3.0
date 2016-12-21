package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-13.
 */
public class BookRoom extends BaseEntity {
    private String bookSerial;//订单号
    private String roomId;//房号
    private String roomPrice;//房价
    private String roomCategory;//房类
    private boolean opened;//已开标志

    public BookRoom() {
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public void setBookSerial(String bookSerial) {
        this.bookSerial = bookSerial;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
