package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-02-09.
 */
public class GuestIntegration extends BaseEntity{
    private String cardId;
    private String country;
    private String selfAccount;

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
}
