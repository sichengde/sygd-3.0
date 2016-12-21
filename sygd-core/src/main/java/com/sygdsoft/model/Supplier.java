package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Supplier extends BaseEntity{
    private String name;//供应商名称

    public Supplier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
