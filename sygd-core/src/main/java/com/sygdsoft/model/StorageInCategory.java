package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class StorageInCategory extends BaseEntity{
    private String category;//入库类型

    public StorageInCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
