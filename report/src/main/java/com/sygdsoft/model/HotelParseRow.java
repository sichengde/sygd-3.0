package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class HotelParseRow {
    private String pointOfSale;//营业部门
    private String dayTotal;//当日累计
    private String monthTotal;//当月累计
    private String yearTotal;//当年累计
    private String dayHistoryTotal;//当日历史同期
    private String monthHistoryTotal;//当月历史同期
    private String yearHistoryTotal;//当年历史同期
    private String module;//所属模块，便于二次曲线图的生成，前端不予以显示
    private String fatherFirstPointOfSale;//是否是二级营业部门，便于二次曲线图的生成，前端不予以显示

    public HotelParseRow() {
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(String dayTotal) {
        this.dayTotal = dayTotal;
    }

    public String getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(String monthTotal) {
        this.monthTotal = monthTotal;
    }

    public String getYearTotal() {
        return yearTotal;
    }

    public void setYearTotal(String yearTotal) {
        this.yearTotal = yearTotal;
    }

    public String getDayHistoryTotal() {
        return dayHistoryTotal;
    }

    public void setDayHistoryTotal(String dayHistoryTotal) {
        this.dayHistoryTotal = dayHistoryTotal;
    }

    public String getMonthHistoryTotal() {
        return monthHistoryTotal;
    }

    public void setMonthHistoryTotal(String monthHistoryTotal) {
        this.monthHistoryTotal = monthHistoryTotal;
    }

    public String getYearHistoryTotal() {
        return yearHistoryTotal;
    }

    public void setYearHistoryTotal(String yearHistoryTotal) {
        this.yearHistoryTotal = yearHistoryTotal;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFatherFirstPointOfSale() {
        return fatherFirstPointOfSale;
    }

    public void setFatherFirstPointOfSale(String fatherFirstPointOfSale) {
        this.fatherFirstPointOfSale = fatherFirstPointOfSale;
    }
}
