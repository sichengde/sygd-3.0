package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-07-15.
 */
public class CompanyCategory extends BaseEntity{
    private String category;
    private String availablePos;
    private String guestSource;

    public CompanyCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailablePos() {
        return availablePos;
    }

    public void setAvailablePos(String availablePos) {
        this.availablePos = availablePos;
    }

    public String getGuestSource() {
        return guestSource;
    }

    public void setGuestSource(String guestSource) {
        this.guestSource = guestSource;
    }
}
