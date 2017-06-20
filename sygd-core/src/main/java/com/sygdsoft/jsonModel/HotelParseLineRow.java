package com.sygdsoft.jsonModel;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class HotelParseLineRow {
    private Date date;
    private Double money;
    private Integer num;

    public HotelParseLineRow() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
