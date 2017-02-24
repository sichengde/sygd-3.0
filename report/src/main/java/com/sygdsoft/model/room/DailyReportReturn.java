package com.sygdsoft.model.room;

import com.sygdsoft.model.DailyReport;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-24.
 */
public class DailyReportReturn {
    private List<DailyReport> dailyReportList;
    private Double companyDebt;
    private Double companyPay;
    private Double vipPay;
    private Double bookMoney;

    public DailyReportReturn() {
    }

    public List<DailyReport> getDailyReportList() {
        return dailyReportList;
    }

    public void setDailyReportList(List<DailyReport> dailyReportList) {
        this.dailyReportList = dailyReportList;
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

    public Double getBookMoney() {
        return bookMoney;
    }

    public void setBookMoney(Double bookMoney) {
        this.bookMoney = bookMoney;
    }
}
