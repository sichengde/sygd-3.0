package com.sygdsoft.model.room;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 舒展 on 2017-02-08.
 */
public class RoomParseReportRow {
    private String month;//月份
    private String averageRent;//住客率
    private String averagePrice;//平均房价
    private String revper;//总房价除以客房总数
    private Integer guestNum;//接待人次
    private Integer groupNum;//接待团队
    private Integer foreigner;//外宾
    private List<String> income;//收入数组
    private List<String> incomeTitle;//收入数组表头
    private Integer guestSourceIndex;//从第几位开始客源分析结束
    private String total;

    public RoomParseReportRow() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAverageRent() {
        return averageRent;
    }

    public void setAverageRent(String averageRent) {
        this.averageRent = averageRent;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getRevper() {
        return revper;
    }

    public void setRevper(String revper) {
        this.revper = revper;
    }

    public Integer getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(Integer guestNum) {
        this.guestNum = guestNum;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getForeigner() {
        return foreigner;
    }

    public void setForeigner(Integer foreigner) {
        this.foreigner = foreigner;
    }

    public List<String> getIncome() {
        return income;
    }

    public void setIncome(List<String> income) {
        this.income = income;
    }

    public List<String> getIncomeTitle() {
        return incomeTitle;
    }

    public void setIncomeTitle(List<String> incomeTitle) {
        this.incomeTitle = incomeTitle;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getGuestSourceIndex() {
        return guestSourceIndex;
    }

    public void setGuestSourceIndex(Integer guestSourceIndex) {
        this.guestSourceIndex = guestSourceIndex;
    }
}
