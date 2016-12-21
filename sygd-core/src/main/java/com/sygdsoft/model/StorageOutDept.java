package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class StorageOutDept extends BaseEntity{
    private String name;//部门名称
    private String saveMan;//领用人

    public StorageOutDept() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaveMan() {
        return saveMan;
    }

    public void setSaveMan(String saveMan) {
        this.saveMan = saveMan;
    }
}
