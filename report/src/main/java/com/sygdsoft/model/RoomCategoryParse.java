package com.sygdsoft.model;

import com.sygdsoft.jsonModel.report.RoomCategoryRow;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class RoomCategoryParse {
    private List<RoomCategoryRow> roomCategoryRowList;
    private List<RoomCategoryRow> roomCategoryRowHistoryList;
    private String remark;//备注显示接待宾客，团队，vip，外宾
    private Map roomCategoryMap;

    public RoomCategoryParse() {
    }

    public List<RoomCategoryRow> getRoomCategoryRowList() {
        return roomCategoryRowList;
    }

    public void setRoomCategoryRowList(List<RoomCategoryRow> roomCategoryRowList) {
        this.roomCategoryRowList = roomCategoryRowList;
    }

    public Map getRoomCategoryMap() {
        return roomCategoryMap;
    }

    public void setRoomCategoryMap(Map roomCategoryMap) {
        this.roomCategoryMap = roomCategoryMap;
    }

    public List<RoomCategoryRow> getRoomCategoryRowHistoryList() {
        return roomCategoryRowHistoryList;
    }

    public void setRoomCategoryRowHistoryList(List<RoomCategoryRow> roomCategoryRowHistoryList) {
        this.roomCategoryRowHistoryList = roomCategoryRowHistoryList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
