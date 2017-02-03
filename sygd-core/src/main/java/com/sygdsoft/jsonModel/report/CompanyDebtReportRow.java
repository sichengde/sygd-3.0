package com.sygdsoft.jsonModel.report;

/**
 * Created by 舒展 on 2017-02-03.
 */
public class CompanyDebtReportRow {
    private String company;//单位名称
    private Double remain;//期初挂账
    private Double debt;//本期挂账

    public CompanyDebtReportRow() {
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
}
