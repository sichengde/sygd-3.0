package com.sygdsoft.model;

/**
 * Created by 舒展 on 2016-03-19.
 * 操作员表
 */
public class User extends BaseEntity {
    private String userId;//操作员号-可以直接输入姓名
    private String password;//操作员密码
    private String permissionArray;//权限数组，操作员所拥有的权限在数据库中是按字符串的形式存储的
    private String moduleArray;//模块数组，操作员所拥有的模块在数据库中是按字符串的形式存储的
    private String pointOfSaleArray;//营业部门数组，操作员所拥有的营业部门在数据库中是按字符串的形式存储的（这个只限于餐饮，接待默认就接待一个，桑拿默认也就桑拿一个）
    private Integer maxDiscount;//营业部门数组，操作员所拥有的营业部门在数据库中是按字符串的形式存储的（这个只限于餐饮，接待默认就接待一个，桑拿默认也就桑拿一个）

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermissionArray() {
        return permissionArray;
    }

    public void setPermissionArray(String permissionArray) {
        this.permissionArray = permissionArray;
    }

    public String getModuleArray() {
        return moduleArray;
    }

    public void setModuleArray(String moduleArray) {
        this.moduleArray = moduleArray;
    }

    public String getPointOfSaleArray() {
        return pointOfSaleArray;
    }

    public void setPointOfSaleArray(String pointOfSale) {
        this.pointOfSaleArray = pointOfSale;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
