package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public class CheckInHistory extends BaseEntity {
    private String cardId;//身份证号
    private String cardType;//证件种类
    private String name;//姓名
    private Date birthdayTime;//生日
    private String sex;//性别
    private String race;//种族
    private String address;//地址
    private String phone;//联系电话
    private Date lastTime;//上一次来店消费
    private String bed;//床位号
    private String doorId;//绑定的房卡
    private String country;//国籍
    private Integer num;//来店次数
    @Transient
    private String roomId;
    @Transient
    private List<CheckInIntegration> checkInIntegrationList;

    public CheckInHistory() {
    }

    public CheckInHistory(CheckInGuest checkInGuest){
        this.cardId= checkInGuest.getCardId();
        this.cardType= checkInGuest.getCardType();
        this.name= checkInGuest.getName();
        this.birthdayTime = checkInGuest.getBirthdayTime();
        this.sex= checkInGuest.getSex();
        this.race= checkInGuest.getRace();
        this.address= checkInGuest.getAddress();
        this.phone= checkInGuest.getPhone();
        this.bed= checkInGuest.getBed();
        this.country= checkInGuest.getCountry();
        this.doorId= checkInGuest.getDoorId();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<CheckInIntegration> getCheckInIntegrationList() {
        return checkInIntegrationList;
    }

    public void setCheckInIntegrationList(List<CheckInIntegration> checkInIntegrationList) {
        this.checkInIntegrationList = checkInIntegrationList;
    }
}
