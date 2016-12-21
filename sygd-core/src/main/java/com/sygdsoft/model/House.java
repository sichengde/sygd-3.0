package com.sygdsoft.model;

/**
 * Created by Administrator on 2016/11/9 0009.
 * 库存的仓库
 */
public class House extends BaseEntity{
    private String houseName;//仓库名称
    private String remark;//备注
    private String includeCategory;//下属类别

    public House() {
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIncludeCategory() {
        return includeCategory;
    }

    public void setIncludeCategory(String includeCategory) {
        this.includeCategory = includeCategory;
    }
}
