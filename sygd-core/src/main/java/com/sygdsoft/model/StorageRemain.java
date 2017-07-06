package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-11-22.
 */
public class StorageRemain {
    private String house;//仓库
    private String cargo;//货品
    private String unit;//单位
    private Double remain;//单价
    private Double price;//余量

    public StorageRemain() {
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
