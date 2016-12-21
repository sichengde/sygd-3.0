package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-13.
 * 客源
 */
public class GuestSource extends BaseEntity {
    private String guestSource;//客源名称

    public GuestSource() {
    }

    public GuestSource(String guestSource) {
        this.guestSource = guestSource;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }
}
