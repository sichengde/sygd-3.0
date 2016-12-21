package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-04-13.
 */
public class OtherParam extends BaseEntity {
    private String otherParam;//代码
    private String value;//数值
    private String module;//模块

    public OtherParam() {
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
