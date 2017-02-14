package com.sygdsoft.model.room;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class RoomSaleReportRow {
    private String category;//类型
    private String today;//当天
    private String month;//当月
    private String year;//当年

    public RoomSaleReportRow() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
