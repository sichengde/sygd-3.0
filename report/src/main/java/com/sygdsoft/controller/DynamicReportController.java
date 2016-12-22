package com.sygdsoft.controller;

import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.service.HotelService;
import com.sygdsoft.service.ReportService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by 舒展 on 2016-03-14.
 * 动态报表控制器，在前端进行某些操作（诸如开房时会生成报表的数据，在开房）
 * 这个报表控制器只是打印一些前台接待特有的单据,严格意义上并不算报表
 */
@Controller
public class DynamicReportController {
    @Autowired
    ReportService reportService2;
    @Autowired
    HotelService hotelService;

    @RequestMapping(value = "receipt/{id}")
    public String one(Model model, @PathVariable("id") Integer id) {
        /*从session当中获取reportService*/
        ReportService reportService = reportService2.getMap().get(id);
        if(reportService==null){
            return "此报表已过期";
        }
        //reportService2.getMap().remove(id);//调试时先不删除，统一放在夜审时删除
        List<FieldTemplate> fieldTemplateList=reportService.getTemplateList();
        if(fieldTemplateList==null){
            JREmptyDataSource jrEmptyDataSource=new JREmptyDataSource();
            model.addAttribute("jrMainDataSource", jrEmptyDataSource);
        }else {
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(reportService.getTemplateList());
            model.addAttribute("jrMainDataSource", jrDataSource);
        }
        // 动态指定报表模板url
        model.addAttribute("url", "file:///C:/report/" + reportService.getName() + ".jasper");
        String format=reportService.getFormat();
        if(format==null){
            format="pdf";
        }
        model.addAttribute("format", format); // 报表格式
        for (int i = 0; i < reportService.getParameters().length; i++) {
            String s = reportService.getParameters()[i];
            model.addAttribute("parameter" + String.valueOf(i + 1), s);
        }

        return "reportView"; // 对应jasper-views.xml中的bean id
    }

}
