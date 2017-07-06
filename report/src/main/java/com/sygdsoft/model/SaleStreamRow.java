package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SaleStreamRow {
    private String foodName;
    private Double num;
    private Double total;
    public SaleStreamRow() {
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
