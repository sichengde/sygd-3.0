package com.sygdsoft.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/9/25 0025.
 * jq的用法是领先于ck的
 */
public class ExchangeUserJQ {
    private List<ExchangeUserRow> exchangeUserRowList;//每行的数据
    private ReportJson reportJson;//子报表用到的时间等等(保存当前报表生成时的查询数据)
    private Double payTotal;//结算款合计
    private Double moneyIn;//杂单
    private Double moneyOut;//冲账
    private Double depositAll;//在店押金
    private Double cancelDepositAll;//所有退预付

    public ExchangeUserJQ() {
    }

    public Double getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Double payTotal) {
        this.payTotal = payTotal;
    }

    public List<ExchangeUserRow> getExchangeUserRowList() {
        return exchangeUserRowList;
    }

    public void setExchangeUserRowList(List<ExchangeUserRow> exchangeUserRowList) {
        this.exchangeUserRowList = exchangeUserRowList;
    }

    public ReportJson getReportJson() {
        return reportJson;
    }

    public void setReportJson(ReportJson reportJson) {
        this.reportJson = reportJson;
    }

    public Double getMoneyIn() {
        return moneyIn;
    }

    public void setMoneyIn(Double moneyIn) {
        this.moneyIn = moneyIn;
    }

    public Double getMoneyOut() {
        return moneyOut;
    }

    public void setMoneyOut(Double moneyOut) {
        this.moneyOut = moneyOut;
    }

    public Double getDepositAll() {
        return depositAll;
    }

    public void setDepositAll(Double depositAll) {
        this.depositAll = depositAll;
    }

    public Double getCancelDepositAll() {
        return cancelDepositAll;
    }

    public void setCancelDepositAll(Double cancelDepositAll) {
        this.cancelDepositAll = cancelDepositAll;
    }
}
