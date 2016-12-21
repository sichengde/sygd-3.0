package com.sygdsoft.model;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-01.
 */
public class SzTable {
    private List<FieldTemplate> templateList;//fields
    private Integer[] widthList;
    private Integer totalWidth;
    private String[] parameters;//参数
    private String[] columnHeaders;//表头参数
    private String format;//报表格式pdf/xls

    public SzTable() {
    }

    public List<FieldTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<FieldTemplate> templateList) {
        this.templateList = templateList;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer[] getWidthList() {
        return widthList;
    }

    public void setWidthList(Integer[] widthList) {
        this.widthList = widthList;
    }

    public Integer getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(Integer totalWidth) {
        this.totalWidth = totalWidth;
    }

    public String[] getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(String[] columnHeaders) {
        this.columnHeaders = columnHeaders;
    }
}
