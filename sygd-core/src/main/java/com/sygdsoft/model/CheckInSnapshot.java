package com.sygdsoft.model;

import java.util.Date;

public class CheckInSnapshot extends CheckIn {
    private Date reportDate;
    private String guestName;
    private Double partInDeposit;

    public CheckInSnapshot() {
    }

    public CheckInSnapshot(CheckIn checkIn) {
        this.setRoomId(checkIn.getRoomId());
        this.setRoomCategory(checkIn.getRoomCategory());
        this.setSelfAccount(checkIn.getSelfAccount());
        this.setGroupAccount(checkIn.getGroupAccount());
        this.setReachTime(checkIn.getReachTime());
        this.setLeaveTime(checkIn.getLeaveTime());
        this.setGuestSource(checkIn.getGuestSource());
        this.setImportant(checkIn.getImportant());
        this.setVip(checkIn.getVip());
        this.setBreakfast(checkIn.getBreakfast());
        this.setRemark(checkIn.getRemark());
        this.setFinalRoomPrice(checkIn.getFinalRoomPrice());
        this.setProtocol(checkIn.getProtocol());
        this.setCompany(checkIn.getCompany());
        this.setVipNumber(checkIn.getVipNumber());
        this.setDeposit(checkIn.getDeposit());
        this.setConsume(checkIn.getConsume());
        this.setPay(checkIn.getPay());
        this.setRoomPriceCategory(checkIn.getRoomPriceCategory());
        this.setUserId(checkIn.getUserId());
        this.setGroupName(checkIn.getGroupName());
        this.setIfRoom(checkIn.getIfRoom());
        this.setRealProtocol(checkIn.getRealProtocol());
        this.setDisableCheckOut(checkIn.getDisableCheckOut());
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Double getPartInDeposit() {
        return partInDeposit;
    }

    public void setPartInDeposit(Double partInDeposit) {
        this.partInDeposit = partInDeposit;
    }
}
