package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-12-01.
 */
public class SaunaDetailHistory extends BaseEntity{
    private String saunaSerial;
    private String saunaMenu;
    private Double price;
    private Integer num;
    private String ring;
    private String userId;
    private Date doTime;
    private Date doneTime;
    private String menuSign;
    private String unit;//单位
    private String remark;//备注
    private String category;//类别
    private String saunaUser;//技师
    private String saunaGroupSerial;//技师
    private Double total;
    private Double afterDiscount;
    private Boolean ifDiscount;
    private Boolean cargo;//是否是货物
    private Boolean storageDone;//自动出库是否统计完成
    private Boolean disabled;//被取消（详细解释在deskInHistory）

    public SaunaDetailHistory() {
    }

    public SaunaDetailHistory(SaunaDetail saunaDetail) {
        this.saunaMenu = saunaDetail.getSaunaMenu();
        this.price = saunaDetail.getPrice();
        this.num = saunaDetail.getNum();
        this.ring = saunaDetail.getRing();
        this.userId = saunaDetail.getUserId();
        this.doTime = saunaDetail.getDoTime();
        this.menuSign = saunaDetail.getMenuSign();
        this.unit = saunaDetail.getUnit();
        this.remark = saunaDetail.getRemark();
        this.category = saunaDetail.getCategory();
        this.saunaUser = saunaDetail.getSaunaUser();
        this.ifDiscount = saunaDetail.getIfDiscount();
        this.cargo = saunaDetail.getCargo();
        this.storageDone = saunaDetail.getStorageDone();
        this.saunaGroupSerial = saunaDetail.getSaunaGroupSerial();
    }

    public String getSaunaSerial() {
        return saunaSerial;
    }

    public void setSaunaSerial(String saunaSerial) {
        this.saunaSerial = saunaSerial;
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

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getMenuSign() {
        return menuSign;
    }

    public void setMenuSign(String menuSign) {
        this.menuSign = menuSign;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(Double afterDiscount) {
        this.afterDiscount = afterDiscount;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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
