package com.sygdsoft.model;

import java.util.List;

/**
 * Created by 舒展 on 2016-10-13.
 */
public class JoinRoomPost {
    private List<String> roomAddList;
    private String remark;

    public JoinRoomPost() {
    }

    public List<String> getRoomAddList() {
        return roomAddList;
    }

    public void setRoomAddList(List<String> roomAddList) {
        this.roomAddList = roomAddList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
