package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/11 0011.
 * 批准人
 */
public class Approver extends BaseEntity{
    private String name;//

    public Approver() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
