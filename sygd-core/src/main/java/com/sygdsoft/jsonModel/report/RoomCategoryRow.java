package com.sygdsoft.jsonModel.report;

/**
 * Created by 舒展 on 2016-11-25.
 */
public class RoomCategoryRow {
    private String categoryRoom;//房类
    private Integer total;//总数
    private Integer empty;//空房
    private Integer repair;//维修
    private Integer self;//自用
    private Integer backUp;//备用
    private Integer rent;//出租
    private String allDay;//全日房
    private String addDay;//加收房
    private String hourRoom;//小时房
    private String historyPrice;//历史同期
    private Double totalConsume;//总计房费
    private String averagePrice;//平均房价
    private String revper;//总房价除以客房总数
    private String averageRent;//出租率

    /*查表临时变量*/
    private Double consume;//总计房费
    private String category;//账单类型
    private String count;//数量

    public RoomCategoryRow() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
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

    public Integer getEmpty() {
        return empty;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }

    public Integer getRepair() {
        return repair;
    }

    public void setRepair(Integer repair) {
        this.repair = repair;
    }

    public Integer getSelf() {
        return self;
    }

    public void setSelf(Integer self) {
        this.self = self;
    }

    public Integer getBackUp() {
        return backUp;
    }

    public void setBackUp(Integer backUp) {
        this.backUp = backUp;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCategoryRoom() {
        return categoryRoom;
    }

    public void setCategoryRoom(String categoryRoom) {
        this.categoryRoom = categoryRoom;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public String getRevper() {
        return revper;
    }

    public void setRevper(String revper) {
        this.revper = revper;
    }

    public String getAverageRent() {
        return averageRent;
    }

    public void setAverageRent(String averageRent) {
        this.averageRent = averageRent;
    }
}
