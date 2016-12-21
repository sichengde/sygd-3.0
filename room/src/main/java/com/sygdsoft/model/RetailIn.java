package com.sygdsoft.model;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-21.
 */
public class RetailIn {
    private DebtHistory debtHistory;
    private List<RoomShopDetail> roomShopDetailList;

    public RetailIn() {
    }

    public DebtHistory getDebtHistory() {
        return debtHistory;
    }

    public void setDebtHistory(DebtHistory debtHistory) {
        this.debtHistory = debtHistory;
    }

    public List<RoomShopDetail> getRoomShopDetailList() {
        return roomShopDetailList;
    }

    public void setRoomShopDetailList(List<RoomShopDetail> roomShopDetailList) {
        this.roomShopDetailList = roomShopDetailList;
    }
}
