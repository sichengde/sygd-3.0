package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class HotelParseRow {
    private String pointOfSale;//营业部门
    private Double dayTotal;//当日累计
    private Double monthTotal;//当月累计
    private Double yearTotal;//当年累计
    private Double dayHistoryTotal;//当日历史同期
    private Double monthHistoryTotal;//当月历史同期
    private Double yearHistoryTotal;//当年历史同期
    private String module;//所属模块，便于二次曲线图的生成，前端不予以显示
    private String fatherFirstPointOfSale;//是否是二级营业部门，便于二次曲线图的生成，前端不予以显示

    public HotelParseRow() {
    }

    public HotelParseRow(String pointOfSale,Double dayTotal, Double monthTotal, Double yearTotal, Double dayHistoryTotal, Double monthHistoryTotal, Double yearHistoryTotal) {
        this.pointOfSale=pointOfSale;
        this.dayTotal = dayTotal;
        this.monthTotal = monthTotal;
        this.yearTotal = yearTotal;
        this.dayHistoryTotal = dayHistoryTotal;
        this.monthHistoryTotal = monthHistoryTotal;
        this.yearHistoryTotal = yearHistoryTotal;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Double getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(Double dayTotal) {
        this.dayTotal = dayTotal;
    }

    public Double getMonthTotal() {
        return monthTotal;
    }

    public void setMonthTotal(Double monthTotal) {
        this.monthTotal = monthTotal;
    }

    public Double getYearTotal() {
        return yearTotal;
    }

    public void setYearTotal(Double yearTotal) {
        this.yearTotal = yearTotal;
    }

    public Double getDayHistoryTotal() {
        return dayHistoryTotal;
    }

    public void setDayHistoryTotal(Double dayHistoryTotal) {
        this.dayHistoryTotal = dayHistoryTotal;
    }

    public Double getMonthHistoryTotal() {
        return monthHistoryTotal;
    }

    public void setMonthHistoryTotal(Double monthHistoryTotal) {
        this.monthHistoryTotal = monthHistoryTotal;
    }

    public Double getYearHistoryTotal() {
        return yearHistoryTotal;
    }

    public void setYearHistoryTotal(Double yearHistoryTotal) {
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
