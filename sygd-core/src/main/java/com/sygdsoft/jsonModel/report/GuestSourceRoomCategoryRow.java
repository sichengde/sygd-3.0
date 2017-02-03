package com.sygdsoft.jsonModel.report;

/**
 * Created by 舒展 on 2017-02-03.
 */
public class GuestSourceRoomCategoryRow {
    private String guestSource;//客源
    private String roomCategory;//房类
    private Double consume;//金额

    public GuestSourceRoomCategoryRow() {
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }
}
