package com.sygdsoft.service;

import com.sygdsoft.mapper.StorageInMapper;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.model.StorageIn;
import com.sygdsoft.model.StorageInDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-11-14.
 */
@Service
@SzMapper(id = "storageIn")
public class StorageInService extends BaseService<StorageIn>{
    @Autowired
    StorageInDetailService storageInDetailService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService userService;
    @Autowired
    StorageInMapper storageInMapper;
    /**
     * 入库
     */
    public Integer storageInAdd(List<StorageInDetail> storageInDetailList,StorageIn storageIn) throws Exception {
        serialService.setStorageInSerial();
        timeService.setNow();
        storageIn.setStorageInSerial(serialService.getStorageInSerial());
        storageIn.setInTime(timeService.getNow());
        List<FieldTemplate> templateList = new ArrayList<>();
        for (StorageInDetail storageInDetail : storageInDetailList) {
            storageInDetail.setStorageInSerial(serialService.getStorageInSerial());
            storageInDetail.setRemain(storageInDetail.getNum());
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(storageInDetail.getHouse());
            fieldTemplate.setField2(storageInDetail.getCargo());
            fieldTemplate.setField3(storageInDetail.getUnit());
            fieldTemplate.setField4(ifNotNullGetString(storageInDetail.getNum()));
            fieldTemplate.setField5(ifNotNullGetString(storageInDetail.getPrice()));
            fieldTemplate.setField6(ifNotNullGetString(storageInDetail.getTotal()));
            fieldTemplate.setField7(storageInDetail.getSupplier());
            fieldTemplate.setField8(timeService.dateToStringShort(storageInDetail.getBeginTime()));
            fieldTemplate.setField9(timeService.dateToStringShort(storageInDetail.getEndTime()));
            fieldTemplate.setField10(storageInDetail.getRemark());
            templateList.add(fieldTemplate);
        }
        add(storageIn);
        storageInDetailService.add(storageInDetailList);
        /*入库单
        * param
        * 1.购买日期
        * 2.入库日期
        * 3.批准人
        * 4.采购员
        * 5.入库类型
        * 6.领用部门
        * 7.凭证号
        * 8.摘要
        * 9.制表人
        * */
        String[] parameters = new String[]{timeService.dateToStringShort(storageIn.getBuyTime()),timeService.dateToStringLong(storageIn.getInTime()),storageIn.getApprover(),storageIn.getBuyer(),storageIn.getCategory(),storageIn.getDeptIn(),storageIn.getStorageInSerial(),storageIn.getRemark(),userService.getCurrentUser()};
        return reportService.generateReport(templateList, parameters,"storageIn","pdf");
    }
}
