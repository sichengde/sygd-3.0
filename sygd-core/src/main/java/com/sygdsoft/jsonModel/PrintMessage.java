package com.sygdsoft.jsonModel;

import com.sygdsoft.model.FieldTemplate;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23 0023.
 */
public class PrintMessage {
    private String printerName;//打印机名称
    private String fileName;//jasper文件名称
    private List<String> paramList;//参数名称
    private List<FieldTemplate> fieldTemplateList;//数组列表

    public PrintMessage() {
    }

    public PrintMessage(List<FieldTemplate> fieldTemplateList,String printerName, String fileName, List<String> paramList) {
        this.fieldTemplateList=fieldTemplateList;
        this.printerName = printerName;
        this.fileName = fileName;
        this.paramList = paramList;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    public List<FieldTemplate> getFieldTemplateList() {
        return fieldTemplateList;
    }

    public void setFieldTemplateList(List<FieldTemplate> fieldTemplateList) {
        this.fieldTemplateList = fieldTemplateList;
    }
}
