package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class SaunaUser extends BaseEntity{
    private String name;//技师
    private String idNumber;//id卡号

    public SaunaUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
