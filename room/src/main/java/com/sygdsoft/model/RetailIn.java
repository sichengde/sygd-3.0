package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-21.
 */
public class RetailIn {
    private List<DebtHistory> debtHistoryList;
    private List<RoomShopDetail> roomShopDetailList;
    private List<CurrencyPost> currencyPostList;
    private String description;

    public RetailIn() {
    }

    public List<DebtHistory> getDebtHistoryList() {
        return debtHistoryList;
    }

    public void setDebtHistoryList(List<DebtHistory> debtHistoryList) {
        this.debtHistoryList = debtHistoryList;
    }

    public List<RoomShopDetail> getRoomShopDetailList() {
        return roomShopDetailList;
    }

    public void setRoomShopDetailList(List<RoomShopDetail> roomShopDetailList) {
        this.roomShopDetailList = roomShopDetailList;
    }

    public List<CurrencyPost> getCurrencyPostList() {
        return currencyPostList;
    }

    public void setCurrencyPostList(List<CurrencyPost> currencyPostList) {
        this.currencyPostList = currencyPostList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
