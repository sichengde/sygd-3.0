package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-11-17.
 */
public class StorageInDept extends BaseEntity{
    private String name;//部门名称
    private String buyer;//采购员

    public StorageInDept() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
