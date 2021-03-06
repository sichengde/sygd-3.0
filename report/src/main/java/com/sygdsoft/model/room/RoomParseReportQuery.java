package com.sygdsoft.model.room;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-08.
 */
public class RoomParseReportQuery {
    private String range;//范围（月，季，年）
    private Date date;//哪天为标准
    private Date beginTime;//明确范围
    private Date endTime;//

    public RoomParseReportQuery() {
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
