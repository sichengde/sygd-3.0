package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-05-19.
 */
public class SaleCount extends BaseEntity {
    private String firstPointOfSale;//一级营业部门
    private String secondPointOfSale;//二级营业部门
    private String name;//统计名称
    private String cookRoom;//厨房-也就是打印机名称

    public SaleCount() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCookRoom() {
        return cookRoom;
    }

    public void setCookRoom(String cookRoom) {
        this.cookRoom = cookRoom;
    }

}
