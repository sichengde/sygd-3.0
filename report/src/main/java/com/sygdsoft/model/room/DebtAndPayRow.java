package com.sygdsoft.model.room;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class DebtAndPayRow {
    private String pointOfSale;//营业部门
    private Double undoneBefore;//期初未结
    private Double debt;//期间发生
    private Double debtPay;//期间结算
    private Double toCompany;//转单位
    private Double lost;//转哑房
    private Double undoneLast;//期末未结

    public DebtAndPayRow() {
    }

    public DebtAndPayRow(String pointOfSale) {
        this.pointOfSale = pointOfSale;
        this.undoneBefore = 0.0;
        this.debt = 0.0;
        this.debtPay = 0.0;
        this.toCompany = 0.0;
        this.lost = 0.0;
        this.undoneLast = 0.0;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Double getUndoneBefore() {
        return undoneBefore;
    }

    public void setUndoneBefore(Double undoneBefore) {
        this.undoneBefore = undoneBefore;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getDebtPay() {
        return debtPay;
    }

    public void setDebtPay(Double debtPay) {
        this.debtPay = debtPay;
    }

    public Double getToCompany() {
        return toCompany;
    }

    public void setToCompany(Double toCompany) {
        this.toCompany = toCompany;
    }

    public Double getLost() {
        return lost;
    }

    public void setLost(Double lost) {
        this.lost = lost;
    }

    public Double getUndoneLast() {
        return undoneLast;
    }

    public void setUndoneLast(Double undoneLast) {
        this.undoneLast = undoneLast;
    }
}