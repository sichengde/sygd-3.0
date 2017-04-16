package com.sygdsoft.model;

import javax.persistence.Transient;

/**
 * Created by 舒展 on 2016-11-17.
 */
public class StorageOutDetail extends BaseEntity {
    private String house;//仓库
    private String cargo;//货物
    private String unit;//单位
    private Integer num;//数量
    private Double price;//单价
    private Double total;//合计金额
    private String myUsage;//用途
    private String storageOutSerial;//序列号
    private String category;//类别
    private Double saleTotal;//销售合计
    @Transient
    private Boolean out;//指定出库

    public StorageOutDetail() {
    }

    public StorageOutDetail(StorageOutDetail storageOutDetail) {
        this.house = storageOutDetail.getHouse();
        this.cargo = storageOutDetail.getCargo();
        this.unit = storageOutDetail.getUnit();
        this.num = storageOutDetail.getNum();
        this.price = storageOutDetail.getPrice();
        this.total = storageOutDetail.getTotal();
        this.myUsage = storageOutDetail.getMyUsage();
        this.storageOutSerial = storageOutDetail.getStorageOutSerial();
        this.category = storageOutDetail.getCategory();
        this.saleTotal = storageOutDetail.getSaleTotal();
        this.out = storageOutDetail.getOut();
    }

    public Boolean getNotNullOut(){
        if(out==null){
            return false;
        }else {
            return out;
        }
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMyUsage() {
        return myUsage;
    }

    public void setMyUsage(String myUsage) {
        this.myUsage = myUsage;
    }

    public String getStorageOutSerial() {
        return storageOutSerial;
    }

    public void setStorageOutSerial(String storageOutSerial) {
        this.storageOutSerial = storageOutSerial;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getOut() {
        return out;
    }

    public void setOut(Boolean out) {
        this.out = out;
    }

    public Double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Double saleTotal) {
        this.saleTotal = saleTotal;
    }
}
