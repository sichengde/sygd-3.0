package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-01-23.
 */
public class RoomStateReportRow {
    private String name;//统计名称（客房总数，维修房...）
    private String total;//数值

    public RoomStateReportRow() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
