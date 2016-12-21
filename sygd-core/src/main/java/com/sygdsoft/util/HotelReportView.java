package com.sygdsoft.util;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import java.util.Map;

/**
 * Created by 舒展 on 2015-12-04.
 * 用于填充jasper报表
 */
public class HotelReportView extends JasperReportsMultiFormatView {
    private JasperReport jasperReport;

    public HotelReportView() {
        super();
    }

    @Override
    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
        if (model.containsKey("url")) {
            setUrl(String.valueOf(model.get("url")));
            this.jasperReport = loadReport();
        }
        return super.fillReport(model);
    }

    @Override
    protected JasperReport getReport() {
        return this.jasperReport;
    }
}
