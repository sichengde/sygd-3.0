package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class StorageOutCategory extends BaseEntity{
    private String category;//出库类型

    public StorageOutCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
