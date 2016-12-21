package com.sygdsoft.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class RoomCategoryParse {
    private List<RoomCategoryRow> roomCategoryRowList;
    private List<RoomCategoryRow> roomCategoryRowHistoryList;
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
}
