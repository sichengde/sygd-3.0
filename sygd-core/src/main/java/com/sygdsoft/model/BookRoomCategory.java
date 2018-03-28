package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class BookRoomCategory extends BaseEntity{
    private String bookSerial;//预定号
    private String roomCategory;//房间类别
    private Integer num;//房间数量

    public BookRoomCategory() {
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public void setBookSerial(String bookSerial) {
        this.bookSerial = bookSerial;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
