package com.sygdsoft.model;

import com.sygdsoft.jsonModel.report.HeadField;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-16.
 */
public class DeskInHistoryParseOut {
    private List<FieldTemplate> fieldTemplateList;
    private List<HeadField> headFieldList;
    private String remark;

    public DeskInHistoryParseOut() {
    }

    public List<FieldTemplate> getFieldTemplateList() {
        return fieldTemplateList;
    }

    public void setFieldTemplateList(List<FieldTemplate> fieldTemplateList) {
        this.fieldTemplateList = fieldTemplateList;
    }

    public List<HeadField> getHeadFieldList() {
        return headFieldList;
    }

    public void setHeadFieldList(List<HeadField> headFieldList) {
        this.headFieldList = headFieldList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
