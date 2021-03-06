package com.sygdsoft.jsonModel;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class GuestParseRow {
    private String guestSource;//客源
    private Integer guestNum;//人数
    private List<String> titleList;
    private List<String> titleValueList;

    public GuestParseRow() {
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public Integer getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(Integer guestNum) {
        this.guestNum = guestNum;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getTitleValueList() {
        return titleValueList;
    }

    public void setTitleValueList(List<String> titleValueList) {
        this.titleValueList = titleValueList;
    }
}
