package com.sygdsoft.model.room;

/**
 * Created by 舒展 on 2017-03-20.
 */
public class DepositUserRow {
    private String user;
    private String currency;
    private String deposit;

    public DepositUserRow() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
