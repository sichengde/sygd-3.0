package com.sygdsoft.model.room;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class DebtAndPayReturn {
    private List<DebtAndPayRow> debtAndPayRowList;
    private Double companyDebt;
    private Double companyPay;
    private Double vipPay;

    public DebtAndPayReturn() {
    }

    public List<DebtAndPayRow> getDebtAndPayRowList() {
        return debtAndPayRowList;
    }

    public void setDebtAndPayRowList(List<DebtAndPayRow> debtAndPayRowList) {
        this.debtAndPayRowList = debtAndPayRowList;
    }

    public Double getCompanyDebt() {
        return companyDebt;
    }

    public void setCompanyDebt(Double companyDebt) {
        this.companyDebt = companyDebt;
    }

    public Double getCompanyPay() {
        return companyPay;
    }

    public void setCompanyPay(Double companyPay) {
        this.companyPay = companyPay;
    }

    public Double getVipPay() {
        return vipPay;
    }

    public void setVipPay(Double vipPay) {
        this.vipPay = vipPay;
    }
}
