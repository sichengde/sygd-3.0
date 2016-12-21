package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-10-21.
 */
public class MenuRemark extends BaseEntity{
    private String Remark;

    public MenuRemark() {
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
