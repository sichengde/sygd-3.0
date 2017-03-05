package com.sygdsoft.jsonModel;

import com.sygdsoft.model.CompanyDebt;
import com.sygdsoft.model.DebtHistory;

import java.util.List;
import java.util.Map;

/**
 * Created by 舒展 on 2016-09-28.
 */
public class CompanyPost {
    private String companyName;
    private String remark;
    private Double debt;
    private Double pay;
    private List<CompanyDebt> companyDebtList;//结账列表
    private List<DebtHistory> debtHistoryList;//精确结算列表
    private Map<String,Double> paySerialMap;//精确结算列表,结账序列号，消费map
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

    public List<DebtHistory> getDebtHistoryList() {
        return debtHistoryList;
    }

    public void setDebtHistoryList(List<DebtHistory> debtHistoryList) {
        this.debtHistoryList = debtHistoryList;
    }

    public Map<String, Double> getPaySerialMap() {
        return paySerialMap;
    }

    public void setPaySerialMap(Map<String, Double> paySerialMap) {
        this.paySerialMap = paySerialMap;
    }
}
