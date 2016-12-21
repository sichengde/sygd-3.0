package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-07-21.
 */
public class VipCategory extends BaseEntity{
    private String category;//会员类别
    private String protocol;//房价协议

    public VipCategory() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
