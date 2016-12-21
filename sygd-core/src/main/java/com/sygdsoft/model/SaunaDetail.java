package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaDetail extends BaseEntity{
    private String saunaMenu;
    private Double price;
    private Integer num;
    private String ring;
    private String userId;
    private Date doTime;
    private String menuSign;//菜品标志，用于区分退菜，fooName有可能被加上/退菜导致联表查询时找不到类别定义
    private String category;//菜品类别
    private String remark;//备注
    private String unit;//单位
    private String saunaUser;//技师
    private String saunaGroupSerial;//团队号
    private Boolean ifDiscount;//是否参与折扣
    private Boolean cargo;//是否是货物
    private Boolean storageDone;//自动出库是否统计完成

    public SaunaDetail() {
    }

    public String getSaunaMenu() {
        return saunaMenu;
    }

    public void setSaunaMenu(String saunaMenu) {
        this.saunaMenu = saunaMenu;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getMenuSign() {
        return menuSign;
    }

    public void setMenuSign(String menuSign) {
        this.menuSign = menuSign;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getIfDiscount() {
        return ifDiscount;
    }

    public void setIfDiscount(Boolean ifDiscount) {
        this.ifDiscount = ifDiscount;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

    public Boolean getStorageDone() {
        return storageDone;
    }

    public void setStorageDone(Boolean storageDone) {
        this.storageDone = storageDone;
    }

    public String getSaunaUser() {
        return saunaUser;
    }

    public void setSaunaUser(String saunaUser) {
        this.saunaUser = saunaUser;
    }

    public String getSaunaGroupSerial() {
        return saunaGroupSerial;
    }

    public void setSaunaGroupSerial(String saunaGroupSerial) {
        this.saunaGroupSerial = saunaGroupSerial;
    }
}
