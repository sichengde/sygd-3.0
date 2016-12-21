package com.sygdsoft.model;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-23.
 * 返回的可视化数组
 */
public class DailyReport {
    private String secondPointOfSale;
    private String paramField1;
    private String paramField2;
    private String paramField3;
    private String paramField4;
    private String paramField5;
    private String total;
    private String mark;//标志是二级营业部门还是币种
    private List<String> paramList;//表头的值,其实就是有几个一级级营业部门的部门名称
    private ReportJson reportJson;//子报表用到的时间等等

    public DailyReport() {
    }

    /*这个由于是不确定有几列，所以需要从后边倒序赋值*/
    public DailyReport(FieldTemplate fieldTemplate,Integer totalField) throws Exception {
        this.secondPointOfSale=fieldTemplate.getField1();
        this.total=fieldTemplate.getFieldN(totalField);
        this.mark=fieldTemplate.getMark();
        while (totalField>2){//最后一列已经赋值为总和了，所以直接先减减
            totalField--;
            setParamFieldN(totalField-1,fieldTemplate.getFieldN(totalField));
        }
    }

    /**
     * 设置第N个域的值
     */
    public void setParamFieldN(Integer index, String value) throws Exception {
        Field field=this.getClass().getDeclaredField("paramField"+index);
        field.set(this,value);
    }
    public String getSecondPointOfSale() {
        return secondPointOfSale;
    }

    public void setSecondPointOfSale(String secondPointOfSale) {
        this.secondPointOfSale = secondPointOfSale;
    }

    public String getParamField1() {
        return paramField1;
    }

    public void setParamField1(String paramField1) {
        this.paramField1 = paramField1;
    }

    public String getParamField2() {
        return paramField2;
    }

    public void setParamField2(String paramField2) {
        this.paramField2 = paramField2;
    }

    public String getParamField3() {
        return paramField3;
    }

    public void setParamField3(String paramField3) {
        this.paramField3 = paramField3;
    }

    public String getParamField4() {
        return paramField4;
    }

    public void setParamField4(String paramField4) {
        this.paramField4 = paramField4;
    }

    public String getParamField5() {
        return paramField5;
    }

    public void setParamField5(String paramField5) {
        this.paramField5 = paramField5;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    public ReportJson getReportJson() {
        return reportJson;
    }

    public void setReportJson(ReportJson reportJson) {
        this.reportJson = reportJson;
    }
}
