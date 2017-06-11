package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by mac on 2017/6/10.
 */
public class StorageOutDetailRich {
    private Integer id;
    private String house;//仓库
    private String cargo;//货物
    private String unit;//单位
    private Integer num;//数量
    private Double total;//合计金额
    private String myUsage;//用途
    private String storageOutSerial;//序列号
    private String category;//类别
    private Double oldPrice;//入库单价
    private Double oldTotal;//入库金额合计
    private Date outTime;//出库时间
    private String approver;//批准人
    private String deptOut;//领用部门
    private String saveMan;//领用人
    private String remark;//备注
    private String userId;//操作员

    public StorageOutDetailRich() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getOldTotal() {
        return oldTotal;
    }

    public void setOldTotal(Double oldTotal) {
        this.oldTotal = oldTotal;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getDeptOut() {
        return deptOut;
    }

    public void setDeptOut(String deptOut) {
        this.deptOut = deptOut;
    }

    public String getSaveMan() {
        return saveMan;
    }

    public void setSaveMan(String saveMan) {
        this.saveMan = saveMan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
