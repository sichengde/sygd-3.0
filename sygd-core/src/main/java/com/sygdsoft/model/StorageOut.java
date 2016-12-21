package com.sygdsoft.model;

import java.util.Date;

/**
 * Created by 舒展 on 2016-11-17.
 */
public class StorageOut extends BaseEntity{
    private Date outTime;//出库时间
    private String approver;//批准人
    private String deptOut;//领用部门
    private String saveMan;//领用人
    private String remark;//备注
    private String storageOutSerial;//序列号
    private String category;//出库类型
    private String userId;//操作员

    public StorageOut() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
