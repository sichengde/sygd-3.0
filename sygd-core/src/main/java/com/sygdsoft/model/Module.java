package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-05-18.
 */
public class Module {
    private String name;//模块名称
    private String link;//跳转该模块主页的链接
    private String[] permission;//该模块拥有的全部权限
    private boolean pointOfSale;//该模块是否拥有营业部门

    public Module() {
    }

    public Module(String name, String link, String[] permission) {
        this.name = name;
        this.link = link;
        this.permission = permission;
        this.pointOfSale=false;
    }
    public Module(String name, String link, String[] permission,boolean pointOfSale) {
        this.name = name;
        this.link = link;
        this.permission = permission;
        this.pointOfSale=pointOfSale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String[] permission) {
        this.permission = permission;
    }

    public boolean getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(boolean pointOfSale) {
        this.pointOfSale = pointOfSale;
    }
}
