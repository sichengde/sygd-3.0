package com.sygdsoft.service;

import com.sygdsoft.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 舒展 on 2016-05-25.
 * 报表服务,在第一波ajax请求中生成需要的报表数据,之后直接打印
 * 通过map来标示需要打印的报表数据
 */
@Service
public class ReportService {
    private List<FieldTemplate> templateList;//fields
    private String[] parameters;//参数
    private String name;//报表名字(.jasper文件)
    private String format;//报表格式pdf/xls
    private Integer reportSerial = 0;
    private Map<Integer, ReportService> map = new ConcurrentHashMap<>();
    @Autowired
    HotelService hotelService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;

    public ReportService() {
    }

    public ReportService(List<FieldTemplate> templateList, String[] parameters, String name, String format) {
        this.templateList = templateList;
        this.parameters = parameters;
        this.name = name;
        this.format = format;
    }

    public Integer generateReport(List<FieldTemplate> templateList, String[] parameters, String name, String format) {
        map.put(getReportSerial(), new ReportService(templateList, parameters, name, format));
        return reportSerial;
    }

    public void clear(){
        map.clear();
        reportSerial=0;
    }

    /**
     * 进行厨房打印
     */
    public void printCook(String printerName, DeskDetail deskDetail,DeskIn deskIn) throws Exception {
        Map<String, Object> param = new HashMap<>();
        /*参数注释
        * 1.桌号
        * 2.菜品
        * 3.数量
        * 4.备注加上叫起标志
        * 5.菜品类别
        * 6.全单备注
        * */
        param.put("parameter1", deskDetail.getDesk());
        param.put("parameter2", deskDetail.getFoodName());
        param.put("parameter3", deskDetail.getNum());
        /*备注和等叫叫起要合在一起*/
        String call = null;
        String remark = deskDetail.getRemark();
        if (deskDetail.getNotNullWaitCall()) {
            call = "等叫";
        }
        if (deskDetail.getNotNullCallUp()) {
            call = "叫起";
        }
        if (call != null && remark != null) {
            param.put("parameter4", remark + "[" + call + "]");
        } else if (call == null && remark != null) {
            param.put("parameter4", remark);
        } else if (call!=null&&remark == null ) {
            param.put("parameter4", call);
        }else if(call==null&&remark == null ){
            param.put("parameter4", null);
        }
        param.put("parameter5", deskDetail.getCategory());
        param.put("parameter6", deskIn.getRemark());
        param.put("parameter7", deskDetail.getNotNullPrice());
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/cookRoom.jasper", param, new JREmptyDataSource());
        this.printByPrinterName(printerName, jasperPrint);
    }

    /**
     * 进行传菜打印
     */
    public void printPassFood(String printerName, List<DeskDetail> deskDetailList,DeskIn deskIn) throws Exception {
        Map<String, Object> param = new HashMap<>();
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(deskDetailList);
        param.put("parameter1", "传菜:  " + deskIn.getDesk());
        param.put("parameter2", timeService.dateToStringLong(deskIn.getDoTime()));
        param.put("parameter3",  userService.getCurrentUser());
        param.put("parameter4",  deskIn.getRemark());
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/passFood.jasper", param, jrDataSource);
        this.printByPrinterName(printerName, jasperPrint);
    }

    /**
     * 进行自助餐打印
     */
    public void printBuffet(String printerName, List<String> paramList) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("parameter1", paramList.get(0));
        param.put("parameter2", paramList.get(1));
        param.put("parameter3", paramList.get(2));
        param.put("parameter4", paramList.get(3));
        param.put("parameter5", paramList.get(4));
        param.put("parameter6", paramList.get(5));
        param.put("parameter7", paramList.get(6));
        param.put("parameter8", paramList.get(7));
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/buffet.jasper", param, new JREmptyDataSource());
        this.printByPrinterName(printerName, jasperPrint);
    }

    /**
     * 传菜和厨房公用的打印片段，根据打印机名称进行打印，直接打印，没有预览
     */
    private void printByPrinterName(String printerName, JasperPrint jasperPrint) throws JRException {
        PrintService[] pss = PrinterJob.lookupPrintServices();
        PrintService ps = null;
        for (PrintService ps1 : pss) {
            String sps = ps1.getName();
            //如果打印机名称相同
            if (sps.equalsIgnoreCase(printerName)) {
                ps = ps1;
            }
        }
        JRAbstractExporter je = new JRPrintServiceExporter();
        je.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, ps);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
        je.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
        try {
            je.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得所有打印机
     */
    public List<String> getAllPrinter() {
        List<String> printName = new ArrayList<>();
        PrintService[] pss = PrinterJob.lookupPrintServices();
        for (int i = 0; i < pss.length; i++) {
            printName.add(pss[i].getName());
        }
        return printName;
    }

    /**
     * ping打印机
     */
    public boolean ping(String address) throws Exception {
        Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
        Process process = null; // 声明处理类对象
        String line = null; // 返回行信息
        InputStream is = null; // 输入流
        InputStreamReader isr = null; // 字节流
        BufferedReader br = null;
        boolean res = false;// 结果
        process = runtime.exec("ping " + address); // PING
        is = process.getInputStream(); // 实例化输入流
        isr = new InputStreamReader(is);// 把输入流转换成字节流
        br = new BufferedReader(isr);// 从字节中读取文本
        while ((line = br.readLine()) != null) {
            if (line.contains("TTL")) {
                res = true;
                break;
            }
        }
        is.close();
        isr.close();
        br.close();
        return res;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReportSerial() {
        return ++reportSerial;
    }

    public void setReportSerial(Integer reportSerial) {
        this.reportSerial = reportSerial;
    }

    public Map<Integer, ReportService> getMap() {
        return map;
    }

    public void setMap(Map<Integer, ReportService> map) {
        this.map = map;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
