package com.sygdsoft.controller;

import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.SzTable;
import com.sygdsoft.service.ReportService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-01.
 */
@RestController
public class SzTableReportController {
    @Autowired
    ReportService reportService;

    /**
     * 参数parameter
     * 1-20.列头
     * 21.标题
     * 22.查询条件
     * 23.总结
     */

    @RequestMapping(value = "szTableReport")
    public Integer szTableReport(@RequestBody SzTable szTable) throws Exception {
        List<FieldTemplate> fieldTemplateList = szTable.getTemplateList();
        String[] paramHeaders=szTable.getColumnHeaders();
        String[] paramOthers=szTable.getParameters();
        String format = szTable.getFormat();
        Integer[] widthList = szTable.getWidthList();//每列的宽度都是多少
        Integer totalWidth = szTable.getTotalWidth();
        JasperDesign design = JRXmlLoader.load("C:/report/szTable.jrxml");
        Integer pageWidth = design.getColumnWidth();//整个页面的宽度
        Integer totalColumn = widthList.length;
        JRElement[] columnHeaders = design.getColumnHeader().getElements();
        JRElement[] fields = design.getDetailSection().getBands()[0].getElements();
        Integer left = 0;
        for (int i = 0; i < 20; i++) {
            if (i < totalColumn) {
                Integer width = pageWidth * widthList[i] / totalWidth;
                columnHeaders[i].setWidth(width);
                columnHeaders[i].setX(left);
                fields[i].setWidth(width);
                fields[i].setX(left);
                left += width;
            } else {
                columnHeaders[i].setWidth(0);
                fields[i].setWidth(0);
                columnHeaders[i].setX(left);
                fields[i].setX(left);
            }
        }
        String[] parameters = new String[23];//szTable固定23个参数
        System.arraycopy(paramHeaders, 0, parameters, 0, totalColumn);
        parameters[20]=paramOthers[0];
        parameters[21]=paramOthers[1];
        parameters[22]=paramOthers[2];
        JasperCompileManager.compileReportToFile(design, "C:/report/szTable.jasper");
        return reportService.generateReport(fieldTemplateList, parameters, "szTable", format);
    }
}
