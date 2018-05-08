package com.sygdsoft.model;

import java.util.Date;

public class DeskDetailCancelAll extends DeskDetail {
    private Date doneTime;
    public DeskDetailCancelAll() {
    }

    public DeskDetailCancelAll(DeskDetail deskDetail) {
        this.setFoodName(deskDetail.getFoodName());
        this.setPrice(deskDetail.getPrice());
        this.setNum(deskDetail.getNum());
        this.setDesk(deskDetail.getDesk());
        this.setUserId(deskDetail.getUserId());
        this.setPointOfSale(deskDetail.getPointOfSale());
        this.setDoTime(deskDetail.getDoTime());
        this.setFoodSign(deskDetail.getFoodSign());
        this.setCategory(deskDetail.getCategory());
        this.setWaitCall(deskDetail.getWaitCall());
        this.setCallUp(deskDetail.getCallUp());
        this.setRemark(deskDetail.getRemark());
        this.setUnit(deskDetail.getUnit());
        this.setCookRoom(deskDetail.getCookRoom());
        this.setIfDiscount(deskDetail.getIfDiscount());
        this.setFoodSet(deskDetail.getFoodSet());
        this.setCargo(deskDetail.getCargo());
        this.setStorageDone(deskDetail.getStorageDone());
        this.setCooked(deskDetail.getCooked());
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }
}
