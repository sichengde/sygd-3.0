package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaMenu extends BaseEntity{
    private String name;//菜名
    private String category;//种类
    private Double price;//单价
    private Boolean ifDiscount;//参与折扣
    private String alias;//别名
    private String unit;//单位
    private Boolean cargo;//库存货品
    private Double cost;//成本
    private Boolean manyPrice;//多重价格

    public SaunaMenu() {
    }

    public Boolean getNotNullManyPrice() {
        if(manyPrice==null){
            return false;
        }else {
            return manyPrice;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Boolean getManyPrice() {
        return manyPrice;
    }

    public void setManyPrice(Boolean manyPrice) {
        this.manyPrice = manyPrice;
    }
}
