package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-12-02.
 */
public class SaunaMenuDetail extends BaseEntity{
    private String menu;//项目名称
    private String inCategory;//账单类型
    private Double price;//价格

    public SaunaMenuDetail() {
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getInCategory() {
        return inCategory;
    }

    public void setInCategory(String inCategory) {
        this.inCategory = inCategory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
