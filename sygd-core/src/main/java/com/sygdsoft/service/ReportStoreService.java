package com.sygdsoft.service;

import com.sygdsoft.mapper.ReportStoreMapper;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.ReportStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SzMapper(id = "reportStore")
public class ReportStoreService extends BaseService<ReportStore> {
    @Autowired
    ReportStoreMapper reportStoreMapper;
    @Autowired
    ReportService reportService;

    public void clear(String name, String identify) {
        reportStoreMapper.clear(name, identify);
    }

    public int print(String name, String identify, String fileName) throws Exception {
        return print(name, identify, fileName, "pdf");
    }

    public int print(String name, String identify, String fileName, String format) throws Exception {
        ReportStore reportStoreQuery = new ReportStore();
        reportStoreQuery.setName(name);
        reportStoreQuery.setIdentify(identify);
        reportStoreQuery.setType("param");
        ReportStore reportStoreParam = reportStoreMapper.selectOne(reportStoreQuery);
        String[] param = new String[20];
        if (reportStoreParam != null) {
            for (int i = 0; i < 20; i++) {
                param[i] = reportStoreParam.getN(i + 1);
            }
        }
        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        reportStoreQuery.setType("fieldTemplate");
        List<ReportStore> reportStoreList = reportStoreMapper.select(reportStoreQuery);
        for (ReportStore reportStore : reportStoreList) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            for (int i = 1; i < 21; i++) {
                fieldTemplate.setFieldN(i, reportStore.getN(i));
            }
            fieldTemplateList.add(fieldTemplate);
        }
        return reportService.generateReport(fieldTemplateList, param, fileName, format);
    }

    public void create(String name, String identify, String[] params, List<FieldTemplate> fieldTemplateList) throws Exception {
        if (params.length > 20) {
            throw new Exception("参数太多");
        }
        this.clear(name, identify);
        ReportStore reportStoreParam = new ReportStore();
        reportStoreParam.setName(name);
        reportStoreParam.setIdentify(identify);
        reportStoreParam.setType("param");
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            reportStoreParam.setN(i + 1, param);
        }
        reportStoreMapper.insert(reportStoreParam);
        List<ReportStore> reportStoreList = new ArrayList<>();
        for (FieldTemplate fieldTemplate : fieldTemplateList) {
            ReportStore reportStore = new ReportStore();
            reportStore.setName(name);
            reportStore.setIdentify(identify);
            reportStore.setType("fieldTemplate");
            reportStore.setColumn_1(fieldTemplate.getField1());
            reportStore.setColumn_2(fieldTemplate.getField2());
            reportStore.setColumn_3(fieldTemplate.getField3());
            reportStore.setColumn_4(fieldTemplate.getField4());
            reportStore.setColumn_5(fieldTemplate.getField5());
            reportStore.setColumn_6(fieldTemplate.getField6());
            reportStore.setColumn_7(fieldTemplate.getField7());
            reportStore.setColumn_8(fieldTemplate.getField8());
            reportStore.setColumn_9(fieldTemplate.getField9());
            reportStore.setColumn_10(fieldTemplate.getField10());
            reportStore.setColumn_11(fieldTemplate.getField11());
            reportStore.setColumn_12(fieldTemplate.getField12());
            reportStore.setColumn_13(fieldTemplate.getField13());
            reportStore.setColumn_14(fieldTemplate.getField14());
            reportStore.setColumn_15(fieldTemplate.getField15());
            reportStore.setColumn_16(fieldTemplate.getField16());
            reportStore.setColumn_17(fieldTemplate.getField17());
            reportStore.setColumn_18(fieldTemplate.getField18());
            reportStore.setColumn_19(fieldTemplate.getField19());
            reportStore.setColumn_20(fieldTemplate.getField20());
            reportStoreList.add(reportStore);
        }
        super.add(reportStoreList);
    }

    public List<ReportStore> getList(String type, String name, String identify) {
        return reportStoreMapper.getList(type, name, identify);
    }
}
