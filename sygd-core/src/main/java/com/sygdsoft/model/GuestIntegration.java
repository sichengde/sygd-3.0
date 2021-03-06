package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-09.
 */
public class GuestIntegration {
    private String cardId;
    private String name;
    private String country;
    private String guestSource;
    private String roomCategory;
    private String selfAccount;
    private Date reachTime;
    private Boolean ifIn;

    public GuestIntegration() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(String selfAccount) {
        this.selfAccount = selfAccount;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }

    public Boolean getIfIn() {
        return ifIn;
    }

    public void setIfIn(Boolean ifIn) {
        this.ifIn = ifIn;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
