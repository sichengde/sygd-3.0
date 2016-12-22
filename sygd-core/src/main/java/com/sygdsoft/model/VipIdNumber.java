package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-07-22.
 */
public class VipIdNumber extends BaseEntity{
    private String idNumber;

    public VipIdNumber() {
    }

    public VipIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
