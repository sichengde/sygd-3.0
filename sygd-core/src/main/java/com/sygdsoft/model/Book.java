package com.sygdsoft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sygdsoft.util.NullJudgement;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 * 预订信息
 */
public class Book extends BaseEntity {
    private String bookSerial;//订单号
    private Date reachTime;//来期
    private Date leaveTime;//预计离期
    private String company;//单位名称
    private String protocol;//房价协议
    private String guestSource;//房价协议
    private String roomPriceCategory;//房价协议
    private Double subscription;//订金
    private String currency;//币种
    private String mark;//备注
    private String phone;//电话
    private Integer totalRoom;//总预定房数
    private Integer bookedRoom;//已开房房数
    private String userId;//操作员号
    private String name;//订单名称
    private Date doTime;//下单时间
    private String state;//订单状态
    private String cardId;//身份证号
    private Date remainTime;//身份证号
    @Transient
    private BookRoom bookRoom;//根据房号查询预定订单时附带的房号信息
    @Transient
    private List<BookRoom> bookRoomList;//确定房数的预定
    @Transient
    private List<BookRoomCategory> bookRoomCategoryList;//不确定房数的预定
    @Transient
    private List<BookMoney> bookMoneyList;

    public Book() {
    }

    public Book(CloudBook cloudBook){
        this.doTime=cloudBook.getDoTime();
        this.reachTime=cloudBook.getReachTime();
        this.leaveTime=cloudBook.getLeaveTime();
        this.guestSource=cloudBook.getGuestSource();
        this.protocol=cloudBook.getProtocol();
        this.currency=cloudBook.getCurrency();
        this.phone=cloudBook.getPhone();
        this.mark=cloudBook.getRemark();
        this.totalRoom=cloudBook.getNum();
        this.remainTime=this.leaveTime;
        this.bookedRoom=0;
        this.subscription=cloudBook.getPrice();
        this.roomPriceCategory="日租房";
        this.name="网络预定"+cloudBook.getName();
        this.state="有效";
    }

    public Double getNotNullScuscription() {
        return NullJudgement.nullToZero(subscription);
    }

    public String getBookSerial() {
        return bookSerial;
    }

    public void setBookSerial(String book_id) {
        this.bookSerial = book_id;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public List<BookRoom> getBookRoomList() {
        return bookRoomList;
    }

    public void setBookRoomList(List<BookRoom> bookRoomList) {
        this.bookRoomList = bookRoomList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BookRoom getBookRoom() {
        return bookRoom;
    }

    public void setBookRoom(BookRoom bookRoom) {
        this.bookRoom = bookRoom;
    }

    public List<BookRoomCategory> getBookRoomCategoryList() {
        return bookRoomCategoryList;
    }

    public void setBookRoomCategoryList(List<BookRoomCategory> bookRoomCategoryList) {
        this.bookRoomCategoryList = bookRoomCategoryList;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Date remainTime) {
        this.remainTime = remainTime;
    }

    public List<BookMoney> getBookMoneyList() {
        return bookMoneyList;
    }

    public void setBookMoneyList(List<BookMoney> bookMoneyList) {
        this.bookMoneyList = bookMoneyList;
    }
}
