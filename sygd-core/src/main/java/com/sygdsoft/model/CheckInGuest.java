package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 * 同住房客
 */
public class CheckInGuest extends BaseEntity{
    private String roomId;//房号
    private String cardId;//身份证号
    private String cardType;//证件种类
    private String name;//姓名
    private Date birthdayTime;//生日
    private String sex;//性别
    private String race;//种族
    private String address;//地址
    private String phone;//联系电话
    private String bed;//床位号(已废除)
    private String doorId;//绑定的门锁特征码
    private String country;//国籍

    public CheckInGuest() {
    }

    public CheckInGuest(CheckInHistory checkInHistory){
        this.roomId=checkInHistory.getRoomId();
        this.cardId= checkInHistory.getCardId();
        this.cardType= checkInHistory.getCardType();
        this.name= checkInHistory.getName();
        this.birthdayTime = checkInHistory.getBirthdayTime();
        this.sex= checkInHistory.getSex();
        this.race= checkInHistory.getRace();
        this.address= checkInHistory.getAddress();
        this.phone= checkInHistory.getPhone();
        this.bed= checkInHistory.getBed();
        this.doorId= checkInHistory.getDoorId();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdayTime() {
        return birthdayTime;
    }

    public void setBirthdayTime(Date birthdayTime) {
        this.birthdayTime = birthdayTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
