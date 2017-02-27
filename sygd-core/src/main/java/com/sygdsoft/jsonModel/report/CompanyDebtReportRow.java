package com.sygdsoft.jsonModel.report;

import com.sygdsoft.util.NullJudgement;

/**
 * Created by 舒展 on 2017-02-03.
 */
public class CompanyDebtReportRow {
    private String company;//单位名称
    private Double remain;//期初余额
    private Double debtGenerate;//本期发生额
    private Double roomConsume;//本期房费
    private Double otherConsume;//其他
    private Double back;//本期回款
    private Double debt;//期末余额

    public CompanyDebtReportRow() {
    }

    public Double getNotNullRemain() {
        return NullJudgement.nullToZero(remain);
    }

    public Double getNotNullDebtGenerate() {
        return NullJudgement.nullToZero(debtGenerate);
    }

    public Double getNotNullRoomConsume() {
        return NullJudgement.nullToZero(roomConsume);
    }

    public Double getNotNullDebt() {
        return NullJudgement.nullToZero(debt);
    }
    public Double getNotNullBack() {
        return NullJudgement.nullToZero(back);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getDebtGenerate() {
        return debtGenerate;
    }

    public void setDebtGenerate(Double debtGenerate) {
        this.debtGenerate = debtGenerate;
    }

    public Double getRoomConsume() {
        return roomConsume;
    }

    public void setRoomConsume(Double roomConsume) {
        this.roomConsume = roomConsume;
    }

    public Double getOtherConsume() {
        return otherConsume;
    }

    public void setOtherConsume(Double otherConsume) {
        this.otherConsume = otherConsume;
    }

    public Double getBack() {
        return back;
    }

    public void setBack(Double back) {
        this.back = back;
    }
}
