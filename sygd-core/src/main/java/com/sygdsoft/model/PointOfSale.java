package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-16.
 * 营业部门
 */
public class PointOfSale extends BaseEntity{
    private String module;
    private String firstPointOfSale;//一级营业部门
    private String secondPointOfSale;//二级营业部门
    private String house;//仓库

    public PointOfSale() {
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFirstPointOfSale() {
        return firstPointOfSale;
    }

    public void setFirstPointOfSale(String firstPointOfSale) {
        this.firstPointOfSale = firstPointOfSale;
    }

    public String getSecondPointOfSale() {
        return secondPointOfSale;
    }

    public void setSecondPointOfSale(String secondPointOfSale) {
        this.secondPointOfSale = secondPointOfSale;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
