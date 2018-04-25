package com.sygdsoft.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class Menu extends BaseEntity {
    private String name;//菜名
    private String category;//种类
    private Double price;//单价
    private Boolean ifDiscount;//参与折扣
    private String pointOfSale;//营业部门（中餐厅，西餐厅）
    private String alias;//别名
    private String unit;//单位
    private String cookRoom;//厨房
    private Boolean sellOut;//卖完
    private Boolean foodSet;//是否是套餐
    private Boolean cargo;//库存货品
    private Double cost;//成本
    private Double remain;//剩余


    public Menu() {
    }

    public Double getNotNullRemain(){
        if(remain==null){
            return 0.0;
        }else {
            return remain;
        }
    }

    public Boolean getNotNullFoodSet(){
        if(foodSet==null){
            return false;
        }else {
            return foodSet;
        }
    }

    public Boolean getNotNullSellOut(){
        if(sellOut==null){
            return false;
        }else {
            return sellOut;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIfDiscount() {
        return ifDiscount;
    }

    public void setIfDiscount(Boolean ifDiscount) {
        this.ifDiscount = ifDiscount;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getSellOut() {
        return sellOut;
    }

    public void setSellOut(Boolean sellOut) {
        this.sellOut = sellOut;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getFoodSet() {
        return foodSet;
    }

    public void setFoodSet(Boolean foodSet) {
        this.foodSet = foodSet;
    }

    public String getCookRoom() {
        return cookRoom;
    }

    public void setCookRoom(String cookRoom) {
        this.cookRoom = cookRoom;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }
}
