package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2017-03-02.
 */
public class POSAndDate {
    private String pointOfSale;
    private Date date;

    public POSAndDate() {
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
