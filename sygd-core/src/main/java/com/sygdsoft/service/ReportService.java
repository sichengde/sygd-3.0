package com.sygdsoft.service;

import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.model.DeskIn;
import com.sygdsoft.model.FieldTemplate;
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
    private List templateList;//fields
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
    @Autowired
    OtherParamService otherParamService;

    public ReportService() {
    }

    public ReportService(List templateList, String[] parameters, String name, String format) {
        this.templateList = templateList;
        this.parameters = parameters;
        this.name = name;
        this.format = format;
    }

    public Integer generateReport(List templateList, String[] parameters, String name, String format) {
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
         * 7.单价
         * 8.单位
         * 9.等叫叫起信息
         * */
        param.put("parameter1", deskDetail.getDesk());
        param.put("parameter2", deskDetail.getFoodName());
        param.put("parameter3", deskDetail.getNum());
        /*备注和等叫叫起要合在一起*/
        String call = "";
        StringBuffer remark = new StringBuffer();
        if(deskDetail.getRemark()!=null) {
            remark.append(deskDetail.getRemark());
        }
        if(deskDetail.getChangeAdd()!=null){
            remark.append(deskDetail.getChangeAdd());
        }
        param.put("parameter5", deskDetail.getCategory());
        param.put("parameter6", deskIn.getRemark());
        param.put("parameter7", deskDetail.getNotNullPrice());
        param.put("parameter8", deskDetail.getUnit());
        if (deskDetail.getNotNullWaitCall()) {
            param.put("parameter9", "[等叫]");
        }
        if (deskDetail.getNotNullCallUp()) {
            param.put("parameter9", "[叫起]");
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/cookRoom.jasper", param, new JREmptyDataSource());
        this.printByPrinterName(printerName, jasperPrint);
    }

    /**
     * 进行传菜打印
     * param:
     * 1.桌号
     * 2.时间
     * 3.操作员
     * 4.整单备注
     */
    public void printPassFood(String printerName, List<DeskDetail> deskDetailList,DeskIn deskIn) throws Exception {
        Integer split=0;
        String splitNum=otherParamService.getValueByName("账单拆分");
        if(splitNum!=null){
            split=Integer.parseInt(splitNum);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("parameter1", "传菜:  " + deskIn.getDesk());
        param.put("parameter2", timeService.dateToStringLong(deskIn.getDoTime()));
        param.put("parameter3", userService.getCurrentUser());
        param.put("parameter4", deskIn.getRemark());
        /*JasperDesign design=JRXmlLoader.load("C:/report/passFood.jrxml");;
        System.out.println(design.getPageHeight());
        design.setPageHeight(4000);
        JasperCompileManager.compileReportToFile(design, "C:/report/passFood.jasper");*/
        /*JRDataSource jrDataSource = new JRBeanCollectionDataSource(deskDetailList);
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/passFood.jasper", param, jrDataSource);
        this.printByPrinterName(printerName, jasperPrint);*/
        if(split==0) {
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(deskDetailList);
            JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/passFood.jasper", param, jrDataSource);
            this.printByPrinterName(printerName, jasperPrint);
        }else {
            List<DeskDetail> dl=new ArrayList<>();
            for (int i = 0; i < deskDetailList.size(); i++) {
                dl.add(deskDetailList.get(i));
                if(i==(split-1)){
                    JRDataSource jrDataSource = new JRBeanCollectionDataSource(dl);
                    JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/passFood.jasper", param, jrDataSource);
                    this.printByPrinterName(printerName, jasperPrint);
                    dl=new ArrayList<>();
                }else if(i%split==split-1){
                    JRDataSource jrDataSource = new JRBeanCollectionDataSource(dl);
                    JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/passFoodNoTitle.jasper", param, jrDataSource);
                    this.printByPrinterName(printerName, jasperPrint);
                    dl=new ArrayList<>();
                }
            }
            if(dl.size()>0) {
                JRDataSource jrDataSource = new JRBeanCollectionDataSource(dl);
                JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/passFoodNoTitle.jasper", param, jrDataSource);
                this.printByPrinterName(printerName, jasperPrint);
            }
        }
    }

    /**
     * 进行直接打印
     */
    public void printDirect(String printerName, List<String> paramList,String fileName) throws Exception {
        Map<String, Object> param = new HashMap<>();
        for (int i = 0; i < paramList.size(); i++) {
            param.put("parameter"+(i+1), paramList.get(i));
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:/report/"+fileName+".jasper", param, new JREmptyDataSource());
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
        //没找到就不打印
        if(ps==null){
            return;
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
