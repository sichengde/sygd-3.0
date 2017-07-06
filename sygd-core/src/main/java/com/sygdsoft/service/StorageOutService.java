package com.sygdsoft.service;

import com.sygdsoft.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-11-17.
 */
@Service
@SzMapper(id = "storageOut")
public class StorageOutService extends BaseService<StorageOut>{
    @Autowired
    StorageOutDetailService storageOutDetailService;
    @Autowired
    StorageInDetailService storageInDetailService;
    @Autowired
    StorageInDetailHistoryService storageInDetailHistoryService;
    @Autowired
    SerialService serialService;
    @Autowired
    TimeService timeService;
    @Autowired
    ReportService reportService;
    @Autowired
    UserService userService;
    /**
     * 出库并生成打印报表
     */
    public Integer storageOutAdd(List<StorageOutDetail> storageOutDetailList,StorageOut storageOut ) throws Exception {
        serialService.setStorageOutSerial();
        timeService.setNow();
        storageOut.setStorageOutSerial(serialService.getStorageOutSerial());
        storageOut.setOutTime(timeService.getNow());
        List<FieldTemplate> templateList = new ArrayList<>();
        List<StorageInDetailHistory> storageInDetailHistoryList = new ArrayList<>();
        List<StorageInDetail> storageInDetailDeleteList = new ArrayList<>();
        for (StorageOutDetail storageOutDetail : storageOutDetailList) {
            storageOutDetail.setStorageOutSerial(serialService.getStorageOutSerial());
            /*分指定出库和未指定出库，冲减库存算法不一样*/
            if (storageOutDetail.getNotNullOut()) {//指定出库
                StorageInDetail storageInDetail=storageInDetailService.getById(storageOutDetail.getId());
                storageOutDetail.setId(null);//有id的话没法插入
                storageInDetail.setRemain(storageInDetail.getRemain()-storageOutDetail.getNum());
                if(storageInDetail.getRemain()==0){//如果余额为0的话就转移到历史表中
                    storageInDetailHistoryList.add(new StorageInDetailHistory(storageInDetail));
                    storageInDetailDeleteList.add(storageInDetail);
                }
                storageInDetailService.update(storageInDetail);
            } else {//不指定，也就是先进先出，冲减库存中的余额
                Double num = storageOutDetail.getNum();
                List<StorageInDetail> storageInDetailList = storageInDetailService.getByCargoExist(storageOutDetail.getHouse(),storageOutDetail.getCargo());
                for (StorageInDetail storageInDetail : storageInDetailList) {
                    Double remain = storageInDetail.getRemain();
                    if (num > remain) {
                        num -= remain;
                        storageInDetail.setRemain(0.0);
                    /*如果余额为0的话就转移到历史表中*/
                        storageInDetailHistoryList.add(new StorageInDetailHistory(storageInDetail));
                        storageInDetailDeleteList.add(storageInDetail);
                    } else if (Objects.equals(num, remain)) {
                        storageInDetail.setRemain(0.0);
                    /*如果余额为0的话就转移到历史表中*/
                        storageInDetailHistoryList.add(new StorageInDetailHistory(storageInDetail));
                        storageInDetailDeleteList.add(storageInDetail);
                        break;
                    } else {
                        storageInDetail.setRemain(remain - num);
                        break;
                    }
                }
                storageInDetailService.update(storageInDetailList);//入库明细余额更新
            }
            /*打印数据生成*/
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(storageOutDetail.getHouse());
            fieldTemplate.setField2(storageOutDetail.getCargo());
            fieldTemplate.setField3(storageOutDetail.getUnit());
            fieldTemplate.setField4(ifNotNullGetString(storageOutDetail.getNum()));
            fieldTemplate.setField5(ifNotNullGetString(storageOutDetail.getPrice()));
            fieldTemplate.setField6(ifNotNullGetString(storageOutDetail.getTotal()));
            fieldTemplate.setField7(storageOutDetail.getMyUsage());
            templateList.add(fieldTemplate);
        }
        add(storageOut);//出库记录
        storageOutDetailService.add(storageOutDetailList);//出库明细
        /*入库余额为0的迁移到历史表中*/
        /*storageInDetailService.delete(storageInDetailDeleteList);
        storageInDetailHistoryService.add(storageInDetailHistoryList);*/
        /*出库单
        * param
        * 1.制表人
        * 2.入库日期
        * 3.批准人
        * 4.采购员
        * 5.入库类型
        * 6.领用部门
        * 7.凭证号
        * 8.摘要
        * */
        String[] parameters = new String[]{userService.getCurrentUser(), timeService.dateToStringLong(storageOut.getOutTime()), storageOut.getApprover(), storageOut.getSaveMan(), storageOut.getCategory(), storageOut.getDeptOut(), storageOut.getStorageOutSerial(), storageOut.getRemark()};
        return reportService.generateReport(templateList, parameters, "storageOut", "pdf");
    }
}
