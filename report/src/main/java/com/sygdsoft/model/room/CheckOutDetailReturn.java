package com.sygdsoft.model.room;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-27.
 */
public class CheckOutDetailReturn {
    private List<CheckOutDetailRow> checkOutDetailRowList;
    private String remark;

    public CheckOutDetailReturn() {
    }

    public List<CheckOutDetailRow> getCheckOutDetailRowList() {
        return checkOutDetailRowList;
    }

    public void setCheckOutDetailRowList(List<CheckOutDetailRow> checkOutDetailRowList) {
        this.checkOutDetailRowList = checkOutDetailRowList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
