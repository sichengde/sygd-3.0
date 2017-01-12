package com.sygdsoft.model;

/**
 * Created by 舒展 on 2017-01-12.
 */
public class InterfaceAddr extends BaseEntity{
    private String name;//名称（身份证读卡器）
    private String ip;//ip地址

    public InterfaceAddr() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
