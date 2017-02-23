package com.sygdsoft.jsonModel;

import com.sygdsoft.model.CompanyDebt;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-28.
 */
public class CompanyPost {
    private String companyName;
    private String remark;
    private Double debt;
    private Double pay;
    private List<CompanyDebt> companyDebtList;//结账列表
    private CurrencyPost currencyPost ;//币种信息

    public CompanyPost() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public List<CompanyDebt> getCompanyDebtList() {
        return companyDebtList;
    }

    public void setCompanyDebtList(List<CompanyDebt> companyDebtList) {
        this.companyDebtList = companyDebtList;
    }

    public CurrencyPost getCurrencyPost() {
        return currencyPost;
    }

    public void setCurrencyPost(CurrencyPost currencyPost) {
        this.currencyPost = currencyPost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
