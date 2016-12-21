package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-11-25.
 */
public class RoomCategoryRow {
    private String category;//房类
    private String total;//总数
    private String allDay;//全日房
    private String addDay;//加收房
    private String hourRoom;//小时房
    private String averagePrice;//平均房价
    private String historyPrice;//历史同期
    private Double totalConsume;//总计房费

    public RoomCategoryRow() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAllDay() {
        return allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public String getAddDay() {
        return addDay;
    }

    public void setAddDay(String addDay) {
        this.addDay = addDay;
    }

    public String getHourRoom() {
        return hourRoom;
    }

    public void setHourRoom(String hourRoom) {
        this.hourRoom = hourRoom;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getHistoryPrice() {
        return historyPrice;
    }

    public void setHistoryPrice(String historyPrice) {
        this.historyPrice = historyPrice;
    }

    public Double getTotalConsume() {
        return totalConsume;
    }

    public void setTotalConsume(Double totalConsume) {
        this.totalConsume = totalConsume;
    }
}
