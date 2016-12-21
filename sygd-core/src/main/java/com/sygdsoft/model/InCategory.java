package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-12-02.
 * 账单类型
 */
public class InCategory extends BaseEntity{
    private String category;//账单类型

    public InCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
