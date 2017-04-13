package com.sygdsoft.model;

import com.sygdsoft.jsonModel.GuestParseRow;

import java.util.List;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class GuestParseReturn {
    private List<GuestParseRow> guestParseRowList;
    private List<String> titleList;
    private List<String> titleValueList;

    public GuestParseReturn() {
    }

    public List<GuestParseRow> getGuestParseRowList() {
        return guestParseRowList;
    }

    public void setGuestParseRowList(List<GuestParseRow> guestParseRowList) {
        this.guestParseRowList = guestParseRowList;
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
