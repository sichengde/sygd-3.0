package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-02-09.
 */
public class GuestMapCheckIn extends BaseEntity{
    private String cardId;//身份证号
    private String selfAccount;//自付账号

    public GuestMapCheckIn() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(String selfAccount) {
        this.selfAccount = selfAccount;
    }
}
