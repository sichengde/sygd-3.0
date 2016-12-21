package com.sygdsoft.model;

import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGroup;
import com.sygdsoft.model.CheckInGuest;

import java.util.List;

/**
 * Created by 舒展 on 2016-05-02.
 */
public class GuestInGroup {
    private List<CheckIn> checkInList;
    private List<CheckInGuest> checkInGuestList;
    private CheckInGroup checkInGroup;
    private Book book;

    public GuestInGroup() {
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
}
