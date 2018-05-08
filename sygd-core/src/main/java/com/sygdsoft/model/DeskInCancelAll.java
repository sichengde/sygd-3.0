package com.sygdsoft.model;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class DeskInCancelAll extends DeskIn {
    private Date doneTime;
    private String userIdDone;
    @Transient
    private List<DeskDetailCancelAll> deskDetailCancelAllList;
    public DeskInCancelAll() {
    }

    public DeskInCancelAll(DeskIn deskIn) {
        this.setDesk(deskIn.getDesk());
        this.setDoTime(deskIn.getDoTime());
        this.setNum(deskIn.getNum());
        this.setConsume(deskIn.getConsume());
        this.setUserId(deskIn.getUserId());
        this.setPointOfSale(deskIn.getPointOfSale());
        this.setRemark(deskIn.getRemark());
        this.setGuestSource(deskIn.getGuestSource());
        this.setSubDeskNum(deskIn.getSubDeskNum());
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getUserIdDone() {
        return userIdDone;
    }

    public void setUserIdDone(String userIdDone) {
        this.userIdDone = userIdDone;
    }

    public List<DeskDetailCancelAll> getDeskDetailCancelAllList() {
        return deskDetailCancelAllList;
    }

    public void setDeskDetailCancelAllList(List<DeskDetailCancelAll> deskDetailCancelAllList) {
        this.deskDetailCancelAllList = deskDetailCancelAllList;
    }
}
