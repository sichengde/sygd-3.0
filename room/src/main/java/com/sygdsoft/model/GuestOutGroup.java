package com.sygdsoft.model;

import com.sygdsoft.jsonModel.CurrencyPost;

import java.util.List;

/**
 * Created by 舒展 on 2016-05-02.
 */
public class GuestOutGroup {
    private Integer groupAccount;
    private List<CurrencyPost> currencyPayList;
    private String remark;
    private Debt debtAdd;//额外的加收房租

    public GuestOutGroup() {
    }

    public Integer getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(Integer groupAccount) {
        this.groupAccount = groupAccount;
    }

    public List<CurrencyPost> getCurrencyPayList() {
        return currencyPayList;
    }

    public void setCurrencyPayList(List<CurrencyPost> currencyPayList) {
        this.currencyPayList = currencyPayList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Debt getDebtAdd() {
        return debtAdd;
    }

    public void setDebtAdd(Debt debtAdd) {
        this.debtAdd = debtAdd;
    }

}
