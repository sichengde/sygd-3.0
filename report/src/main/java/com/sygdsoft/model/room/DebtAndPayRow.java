package com.sygdsoft.model.room;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class DebtAndPayRow {
    private String title;
    private Double debtDay;
    private Double debtMonth;
    private Double debtYear;
    private Double payDay;
    private Double payMonth;
    private Double payYear;

    public DebtAndPayRow() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDebtDay() {
        return debtDay;
    }

    public void setDebtDay(Double debtDay) {
        this.debtDay = debtDay;
    }

    public Double getDebtMonth() {
        return debtMonth;
    }

    public void setDebtMonth(Double debtMonth) {
        this.debtMonth = debtMonth;
    }

    public Double getDebtYear() {
        return debtYear;
    }

    public void setDebtYear(Double debtYear) {
        this.debtYear = debtYear;
    }

    public Double getPayDay() {
        return payDay;
    }

    public void setPayDay(Double payDay) {
        this.payDay = payDay;
    }

    public Double getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Double payMonth) {
        this.payMonth = payMonth;
    }

    public Double getPayYear() {
        return payYear;
    }

    public void setPayYear(Double payYear) {
        this.payYear = payYear;
    }
}
