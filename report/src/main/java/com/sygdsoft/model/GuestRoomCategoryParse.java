package com.sygdsoft.model;

import com.sygdsoft.jsonModel.report.GuestSourceRoomCategoryRow;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-03.
 */
public class GuestRoomCategoryParse {
    private ReportJson reportJson;
    private List<GuestSourceRoomCategoryRow> guestSourceRoomCategoryRowList;

    public GuestRoomCategoryParse() {
    }

    public ReportJson getReportJson() {
        return reportJson;
    }

    public void setReportJson(ReportJson reportJson) {
        this.reportJson = reportJson;
    }

    public List<GuestSourceRoomCategoryRow> getGuestSourceRoomCategoryRowList() {
        return guestSourceRoomCategoryRowList;
    }

    public void setGuestSourceRoomCategoryRowList(List<GuestSourceRoomCategoryRow> guestSourceRoomCategoryRowList) {
        this.guestSourceRoomCategoryRowList = guestSourceRoomCategoryRowList;
    }
}
