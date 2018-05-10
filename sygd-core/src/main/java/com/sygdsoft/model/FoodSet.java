package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-11-02.
 */
public class FoodSet extends BaseEntity{
    private String setName;//套餐名称
    private String foodName;//菜品名称
    private Double foodNum;//菜品数量
    private String pointOfSale;//营业部门
    private String tempFood;//临时菜

    public FoodSet() {
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(Double foodNum) {
        this.foodNum = foodNum;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getTempFood() {
        return tempFood;
    }

    public void setTempFood(String tempFood) {
        this.tempFood = tempFood;
    }
}
