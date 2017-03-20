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
    private Double roomPayDay;//当日结算款(客房)
    private Double deskPayDay;//当日结算款(餐厅)
    private Double companyGenerate;//当日单位发生额(所有开房有单位的)

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

    public Double getRoomPayDay() {
        return roomPayDay;
    }

    public void setRoomPayDay(Double roomPayDay) {
        this.roomPayDay = roomPayDay;
    }

    public Double getDeskPayDay() {
        return deskPayDay;
    }

    public void setDeskPayDay(Double deskPayDay) {
        this.deskPayDay = deskPayDay;
    }

    public Double getCompanyGenerate() {
        return companyGenerate;
    }

    public void setCompanyGenerate(Double companyGenerate) {
        this.companyGenerate = companyGenerate;
    }
}
