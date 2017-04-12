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
    private Integer groupNum;//接待团队
    private Integer foreigner;//外宾
    private List<String> titleValueList;//接待人数
    private List<String> titleList;//接待人数表头
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

    public List<String> getTitleValueList() {
        return titleValueList;
    }

    public void setTitleValueList(List<String> titleValueList) {
        this.titleValueList = titleValueList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }
}
