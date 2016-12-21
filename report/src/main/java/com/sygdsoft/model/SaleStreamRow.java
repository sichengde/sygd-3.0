package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SaleStreamRow {
    private String foodName;
    private Integer num;
    private Double total;
    public SaleStreamRow() {
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
