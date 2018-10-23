package com.sygdsoft.model;

import com.sygdsoft.jsonModel.report.RoomCategoryRow;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class RoomCategoryParse {
    private List<RoomSnapshot> roomSnapshotList;
    private String remark;//备注显示接待宾客，团队，vip，外宾

    public RoomCategoryParse() {
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<RoomSnapshot> getRoomSnapshotList() {
        return roomSnapshotList;
    }

    public void setRoomSnapshotList(List<RoomSnapshot> roomSnapshotList) {
        this.roomSnapshotList = roomSnapshotList;
    }
}
