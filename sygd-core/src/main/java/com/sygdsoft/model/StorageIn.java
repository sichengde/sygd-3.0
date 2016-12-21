package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-11-17.
 */
public class StorageIn extends BaseEntity{
    private Date inTime;//入库时间
    private String approver;//批准人
    private String buyer;//采购员
    private String deptIn;//采购部门
    private String remark;//备注
    private String storageInSerial;//序列号
    private String category;//入库类型
    private String userId;//操作员
    private Date buyTime;//购买时间

    public StorageIn() {
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDeptIn() {
        return deptIn;
    }

    public void setDeptIn(String deptIn) {
        this.deptIn = deptIn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStorageInSerial() {
        return storageInSerial;
    }

    public void setStorageInSerial(String storageInSerial) {
        this.storageInSerial = storageInSerial;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
