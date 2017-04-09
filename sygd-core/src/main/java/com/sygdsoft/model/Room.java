package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-03-22.
 * 房间状态表
 */
public class Room extends BaseEntity{
    private String roomId;//房号
    private String area;//楼区
    private String floor;//楼层
    private String category;//房间类别
    private Double price;//房价,没什么用了，开房房价走协议房价，这里只有换房时用作临时变量，应该声明@Transient，但为了避免修改库表太多，暂时不换了
    private String breakfast;//早餐标志
    private String totalBed;//总共床位
    private String state;//房间状态 对应annotation.RoomState里的常量
    private Boolean dirty;//是否为脏房
    private Date checkOutTime;//最近一次退房时间
    private String repairReason;//维修理由
    private Date repairTime;//维修时间
    private String todayLock;//当日锁房，不锁的时候是null，锁的时候是原因
    private Boolean ifRoom;//是否属于客房，参与销售统计，夜审加房费
    @Transient
    private CheckIn checkIn;
    @Transient
    private List<CheckInGuest> checkInGuestList;
    @Transient
    private List<Book> bookList;
    @Transient
    private CheckInGroup checkInGroup;
    @Transient
    private Boolean longRoom;
    @Transient
    private Boolean birthday;//是否是生日
    @Transient
    private Boolean todayLeave;//今日预离

    public Room() {
    }

    public Room(Room room) {
        this.roomId = room.roomId;
        this.area = room.area;
        this.floor = room.floor;
        this.category = room.category;
        this.price = room.price;
        this.breakfast = room.breakfast;
        this.totalBed = room.totalBed;
        this.state = room.state;
        this.dirty = room.dirty;
        this.checkOutTime = room.checkOutTime;
        this.repairReason = room.repairReason;
        this.repairTime = room.repairTime;
        this.todayLock = room.todayLock;
        this.checkIn = room.checkIn;
        this.checkInGuestList = room.checkInGuestList;
        this.bookList = room.bookList;
        this.checkInGroup = room.checkInGroup;
        this.longRoom = room.longRoom;
    }

    public Boolean getNotNullDirty() {
        if(dirty==null){
            return false;
        }else {
            return dirty;
        }
    }

    public Boolean getNotNullIfRoom() {
        if(ifRoom==null){
            return false;
        }else {
            return ifRoom;
        }
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getTotalBed() {
        return totalBed;
    }

    public void setTotalBed(String totalBed) {
        this.totalBed = totalBed;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getRepairReason() {
        return repairReason;
    }

    public void setRepairReason(String repairReason) {
        this.repairReason = repairReason;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public String getTodayLock() {
        return todayLock;
    }

    public void setTodayLock(String todayLock) {
        this.todayLock = todayLock;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public List<CheckInGuest> getCheckInGuestList() {
        return checkInGuestList;
    }

    public void setCheckInGuestList(List<CheckInGuest> checkInGuestList) {
        this.checkInGuestList = checkInGuestList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public CheckInGroup getCheckInGroup() {
        return checkInGroup;
    }

    public void setCheckInGroup(CheckInGroup checkInGroup) {
        this.checkInGroup = checkInGroup;
    }

    public Boolean getLongRoom() {
        return longRoom;
    }

    public void setLongRoom(Boolean longRoom) {
        this.longRoom = longRoom;
    }

    public Boolean getBirthday() {
        return birthday;
    }

    public void setBirthday(Boolean birthday) {
        this.birthday = birthday;
    }

    public Boolean getTodayLeave() {
        return todayLeave;
    }

    public void setTodayLeave(Boolean todayLeave) {
        this.todayLeave = todayLeave;
    }

    public Boolean getIfRoom() {
        return ifRoom;
    }

    public void setIfRoom(Boolean ifRoom) {
        this.ifRoom = ifRoom;
    }
}
