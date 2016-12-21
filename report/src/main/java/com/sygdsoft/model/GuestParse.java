package com.sygdsoft.model;

import com.sygdsoft.jsonModel.GuestParseRow;

import java.util.List;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class GuestParse {
    private List<GuestParseRow> guestParseRowList;
    private List<GuestParseRow> guestParseRowListHistory;

    public GuestParse() {
    }

    public List<GuestParseRow> getGuestParseRowList() {
        return guestParseRowList;
    }

    public void setGuestParseRowList(List<GuestParseRow> guestParseRowList) {
        this.guestParseRowList = guestParseRowList;
    }

    public List<GuestParseRow> getGuestParseRowListHistory() {
        return guestParseRowListHistory;
    }

    public void setGuestParseRowListHistory(List<GuestParseRow> guestParseRowListHistory) {
        this.guestParseRowListHistory = guestParseRowListHistory;
    }
}
