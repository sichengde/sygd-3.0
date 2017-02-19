package com.sygdsoft.model;

/**
 * Created by Administrator on 2017/2/19 0019.
 */
public class MenuCost extends BaseEntity{
    private String food;//菜品名称
    private String cargo;//货品名称
    private Integer num;//数量

    public MenuCost() {
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
