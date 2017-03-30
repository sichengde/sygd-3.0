package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-03-30.
 */
public class RoomShopDetailWithCurrency {
    private String item;
    private String num;
    private String unit;
    private Double totalMoney;
    private String currency;

    public RoomShopDetailWithCurrency() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
