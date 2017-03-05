package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-09.
 */
public class GuestIntegration extends BaseEntity{
    private String cardId;
    private String country;
    private String selfAccount;
    private Date reachTime;

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
}
