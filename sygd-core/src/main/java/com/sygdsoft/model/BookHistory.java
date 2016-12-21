package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-07-29.
 */
public class BookHistory extends BaseEntity{
    private String bookSerial;//订单号
    private Date reachTime;//来期
    private Date leaveTime;//预计离期
    private String company;//单位名称
    private String guestSource;//单位名称
    private String protocol;//房价协议
    private String roomPriceCategory;//房价协议
    private Double subscription;//订金
    private String mark;//备注
    private String phone;//电话
    private Integer totalRoom;//总预定房数
    private Integer bookedRoom;//已预定房数
    private String userId;//操作员号
    private String name;//订单名称
    private Date doTime;//下单时间
    private String state;//订单状态
    private Date doneTime;//完成时间

    public BookHistory() {
    }

    public BookHistory(Book book) {
        this.bookSerial = book.getBookSerial();
        this.reachTime = book.getReachTime();
        this.leaveTime = book.getLeaveTime();
        this.company = book.getCompany();
        this.guestSource = book.getGuestSource();
        this.protocol = book.getProtocol();
        this.roomPriceCategory = book.getRoomPriceCategory();
        this.subscription = book.getSubscription();
        this.mark = book.getMark();
        this.phone = book.getPhone();
        this.totalRoom = book.getTotalRoom();
        this.bookedRoom = book.getBookedRoom();
        this.userId = book.getUserId();
        this.name = book.getName();
        this.doTime = book.getDoTime();
        this.state = book.getState();
        this.doneTime = new Date();
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public void setBookSerial(String bookSerial) {
        this.bookSerial = bookSerial;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRoomPriceCategory() {
        return roomPriceCategory;
    }

    public void setRoomPriceCategory(String roomPriceCategory) {
        this.roomPriceCategory = roomPriceCategory;
    }

    public Double getSubscription() {
        return subscription;
    }

    public void setSubscription(Double subscription) {
        this.subscription = subscription;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Integer totalRoom) {
        this.totalRoom = totalRoom;
    }

    public Integer getBookedRoom() {
        return bookedRoom;
    }

    public void setBookedRoom(Integer bookedRoom) {
        this.bookedRoom = bookedRoom;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }
}
