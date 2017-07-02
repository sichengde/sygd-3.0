package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class StorageInDetailHistory extends BaseEntity{
    private String house;//仓库
    private String cargo;//货物
    private String unit;//单位
    private Double num;//数量
    private Double price;//单价
    private Double total;//金额合计
    private String supplier;//供应商
    private Date beginTime;//生产日期
    private Date endTime;//库存期限
    private String remark;//备注
    private Double remain;//剩余
    private String storageInSerial;//序列号

    public StorageInDetailHistory() {
    }

    public StorageInDetailHistory(StorageInDetail storageInDetail) {
        this.house=storageInDetail.getHouse();
        this.cargo=storageInDetail.getCargo();
        this.unit=storageInDetail.getUnit();
        this.num=storageInDetail.getNum();
        this.price=storageInDetail.getPrice();
        this.total=storageInDetail.getTotal();
        this.supplier=storageInDetail.getSupplier();
        this.beginTime=storageInDetail.getBeginTime();
        this.endTime=storageInDetail.getEndTime();
        this.remark=storageInDetail.getRemark();
        this.remain=storageInDetail.getRemain();
        this.storageInSerial=storageInDetail.getStorageInSerial();
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

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public String getStorageInSerial() {
        return storageInSerial;
    }

    public void setStorageInSerial(String storageInSerial) {
        this.storageInSerial = storageInSerial;
    }
}
