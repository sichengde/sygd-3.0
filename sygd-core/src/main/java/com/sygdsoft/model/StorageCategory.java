package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/9 0009.
 * 货品类别
 */
public class StorageCategory extends BaseEntity{
    private String category;//货品类别
    private String remark;//备注

    public StorageCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
