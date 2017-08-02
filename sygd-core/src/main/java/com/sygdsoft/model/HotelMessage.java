package com.sygdsoft.model;

/**
 * Created by Administrator on 2017/8/2.
 */
public class HotelMessage extends BaseEntity{
    private String name;
    private String value;

    public HotelMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
