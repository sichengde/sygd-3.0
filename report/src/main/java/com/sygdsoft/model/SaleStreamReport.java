package com.sygdsoft.model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SaleStreamReport {
    private List<SaleStreamRow> saleStreamRowList;
    private Double totalMoney;
    private Integer reportIndex;
    private SaleStreamQuery saleStreamQuery;
    private String categoryParse;

    public SaleStreamReport() {
    }

    public List<SaleStreamRow> getSaleStreamRowList() {
        return saleStreamRowList;
    }

    public void setSaleStreamRowList(List<SaleStreamRow> saleStreamRowList) {
        this.saleStreamRowList = saleStreamRowList;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public SaleStreamQuery getSaleStreamQuery() {
        return saleStreamQuery;
    }

    public void setSaleStreamQuery(SaleStreamQuery saleStreamQuery) {
        this.saleStreamQuery = saleStreamQuery;
    }

    public Integer getReportIndex() {
        return reportIndex;
    }

    public void setReportIndex(Integer reportIndex) {
        this.reportIndex = reportIndex;
    }

    public String getCategoryParse() {
        return categoryParse;
    }

    public void setCategoryParse(String categoryParse) {
        this.categoryParse = categoryParse;
    }
}
