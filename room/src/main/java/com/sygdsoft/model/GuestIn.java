package com.sygdsoft.model;

import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGuest;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-19.
 */
public class GuestIn {
    private List<CheckIn> checkInList;
    private List<CheckInGuest> checkInGuestList;
    private CheckInGroup checkInGroup;
    private Book book;//预定开房
    private String again;//补打标志
    private Protocol protocol;//自定义房价时会传递进来自定义的开房价格

    public GuestIn() {
    }

    public GuestIn(List<CheckIn> checkInList, List<CheckInGuest> checkInGuestList, CheckInGroup checkInGroup, Book book) {
        this.checkInList = checkInList;
        this.checkInGuestList = checkInGuestList;
        this.checkInGroup = checkInGroup;
        this.book = book;
    }

    public List<CheckIn> getCheckInList() {
        return checkInList;
    }

    public void setCheckInList(List<CheckIn> checkInList) {
        this.checkInList = checkInList;
    }

    public List<CheckInGuest> getCheckInGuestList() {
        return checkInGuestList;
    }

    public void setCheckInGuestList(List<CheckInGuest> checkInGuestList) {
        this.checkInGuestList = checkInGuestList;
    }

    public CheckInGroup getCheckInGroup() {
        return checkInGroup;
    }

    public void setCheckInGroup(CheckInGroup checkInGroup) {
        this.checkInGroup = checkInGroup;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getAgain() {
        return again;
    }

    public void setAgain(String again) {
        this.again = again;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
