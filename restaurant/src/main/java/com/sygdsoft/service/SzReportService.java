package com.sygdsoft.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
@Service
public class SzReportService {


    /*public void getAllPrinter() throws Exception {
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/cookRoom.jasper", new HashMap(),new JREmptyDataSource());
//false/true 表示在打印的时候是否显示打印机设置
        //JasperPrintManager.printReport(jasperPrint, false);
        PrintService[] pss = PrinterJob.lookupPrintServices();
        PrintService ps = null;
        for (int i = 0; i < pss.length; i++) {
            String sps = pss[i].toString();
            //如果打印机名称相同
            if(sps.equalsIgnoreCase("Win32 Printer : Microsoft XPS Document Writer")){
                ps = pss[i];
            }
        }
        JRAbstractExporter je = new JRPrintServiceExporter();
        je.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//设置指定打印机
        je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, ps);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
//打印
        je.exportReport();
    }*/
}
