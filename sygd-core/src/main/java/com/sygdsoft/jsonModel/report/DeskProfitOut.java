package com.sygdsoft.jsonModel.report;

/**
 * Created by 舒展 on 2016-11-15.
 */
public class DeskProfitOut {
    private String foodName;//菜品
    private Integer num;//数量
    private Double afterDiscount;//总销售额
    private Double totalCost;//总成本
    private String costRate;//成本毛利率
    private String saleRate;//销售毛利率

    public DeskProfitOut() {
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(Double afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCostRate() {
        return costRate;
    }

    public void setCostRate(String costRate) {
        this.costRate = costRate;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }
}
