package com.sygdsoft.model;

import com.sygdsoft.jsonModel.report.CompanyDebtReportRow;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-03.
 */
public class CompanyDebtReportData {
    private ReportJson reportJson;
    private List<CompanyDebtReportRow> companyDebtReportRowList;

    public CompanyDebtReportData() {
    }

    public ReportJson getReportJson() {
        return reportJson;
    }

    public void setReportJson(ReportJson reportJson) {
        this.reportJson = reportJson;
    }

    public List<CompanyDebtReportRow> getCompanyDebtReportRowList() {
        return companyDebtReportRowList;
    }

    public void setCompanyDebtReportRowList(List<CompanyDebtReportRow> companyDebtReportRowList) {
        this.companyDebtReportRowList = companyDebtReportRowList;
    }
}
