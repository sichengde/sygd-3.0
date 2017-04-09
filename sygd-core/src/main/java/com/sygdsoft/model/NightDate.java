package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by Administrator on 2017-04-08.
 */
public class NightDate extends BaseEntity{
    private Date nightDate;
    private Date nightTime;

    public NightDate() {
    }

    public Date getNightDate() {
        return nightDate;
    }

    public void setNightDate(Date nightDate) {
        this.nightDate = nightDate;
    }

    public Date getNightTime() {
        return nightTime;
    }

    public void setNightTime(Date nightTime) {
        this.nightTime = nightTime;
    }
}
