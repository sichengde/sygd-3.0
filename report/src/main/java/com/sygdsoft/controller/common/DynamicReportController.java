package com.sygdsoft.controller.common;

import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.service.HotelService;
import com.sygdsoft.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void one(@PathVariable("id") Integer id,HttpServletResponse response) throws Exception{
        /*从session当中获取reportService*/
        ReportService reportService = reportService2.getMap().get(id);
        if(reportService==null){
            return ;
        }
        //reportService2.getMap().remove(id);//调试时先不删除，统一放在夜审时删除
        List<FieldTemplate> fieldTemplateList=reportService.getTemplateList();
        JRDataSource jrDataSource;
        if(fieldTemplateList==null){
            jrDataSource=new JREmptyDataSource();
        }else {
            jrDataSource = new JRBeanCollectionDataSource(reportService.getTemplateList());
        }
        // 动态指定报表模板url
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < reportService.getParameters().length; i++) {
            String s = reportService.getParameters()[i];
            params.put("parameter" + String.valueOf(i + 1),s);
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/" + reportService.getName() + ".jasper", params, jrDataSource);
        //Media Type
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        //Export PDF Stream
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}
