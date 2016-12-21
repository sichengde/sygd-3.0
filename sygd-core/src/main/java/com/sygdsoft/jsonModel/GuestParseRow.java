package com.sygdsoft.jsonModel;

import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class GuestParseRow {
    private String guestSource;//客源
    private Integer num;//人数
    private String averageConsume;//平均消费
    private String totalConsume;//消费总计

    public GuestParseRow() {
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAverageConsume() {
        return averageConsume;
    }

    public void setAverageConsume(String averageConsume) {
        this.averageConsume = averageConsume;
    }

    public String getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(String totalConsume) {
        this.totalConsume = totalConsume;
    }
}
