package com.sygdsoft.service;

import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.model.DeskDetailHistory;
import com.sygdsoft.model.FieldTemplate;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2017-03-16.
 */
@Service
public class DeskControllerService {
    @Autowired
    DeskDetailService deskDetailService;
    @Autowired
    DeskDetailHistoryService deskDetailHistoryService;
    @Autowired
    SzMath szMath;
    @Autowired
    OtherParamService otherParamService;

    public void generateDetail(List<DeskDetail> deskDetailList, List<FieldTemplate> templateList) throws Exception {
        if (deskDetailList == null || deskDetailList.size() == 0) {
            return;
        }
        String deskName=deskDetailList.get(0).getDesk();
        String pointOfSale=deskDetailList.get(0).getPointOfSale();
        String lastCategory = "";
        Double categoryTotal = 0.0;
        FieldTemplate fieldTemplateSum = new FieldTemplate();
        /*先把套餐都删掉*/
        Iterator<DeskDetail> it = deskDetailList.iterator();
        while (it.hasNext()) {
            DeskDetail deskDetail = it.next();
            if (deskDetail.getNotNullFoodSet()) {
                it.remove();
            }
            if (!"y".equals(otherParamService.getValueByName("结账包含退菜"))) {
                if (deskDetail.getNum()==0.0){
                    it.remove();
                }
            }
        }
        /*然后再重新查找套餐，其实这个方法很蠢，但没办法，之前的代码太烂*/
        List<DeskDetail> deskDetailListFoodSet = deskDetailService.getListByDesk(deskName, pointOfSale, null, true);
        for (DeskDetail deskDetail : deskDetailListFoodSet) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(deskDetail.getFoodName());
            fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
            fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
            fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
            templateList.add(fieldTemplate);
        }
        for (DeskDetail deskDetail : deskDetailList) {
            if (lastCategory.equals(deskDetail.getCategory())) {
                /*打印信息*/
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetail.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal += deskDetail.getNotNullPrice() * deskDetail.getNum();
            } else {
                if (!lastCategory.equals("")) {
                    fieldTemplateSum.setField5(fieldTemplateSum.getField5() + categoryTotal);
                }
                fieldTemplateSum = new FieldTemplate();
                fieldTemplateSum.setField5(deskDetail.getCategory() + ":");
                lastCategory = deskDetail.getCategory();
                templateList.add(fieldTemplateSum);
                categoryTotal = 0.0;
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetail.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal += deskDetail.getNotNullPrice() * deskDetail.getNum();
            }
        }
        fieldTemplateSum.setField5(szMath.ifNotNullGetString(fieldTemplateSum.getField5()) + categoryTotal);
    }

    public void generateDetailHistory(List<DeskDetailHistory> deskDetailHistoryList, List<FieldTemplate> templateList) {
        if (deskDetailHistoryList == null || deskDetailHistoryList.size() == 0) {
            return;
        }
        String ckSerial=deskDetailHistoryList.get(0).getCkSerial();
        String lastCategory = "";
        Double categoryTotal = 0.0;
        FieldTemplate fieldTemplateSum = new FieldTemplate();
        /*先把套餐都删掉*/
        Iterator<DeskDetailHistory> it = deskDetailHistoryList.iterator();
        while (it.hasNext()) {
            DeskDetailHistory deskDetail = it.next();
            if (deskDetail.getNotNullFoodSet()) {
                it.remove();
            }
        }
        /*然后再重新查找套餐，其实这个方法很蠢，但没办法，之前的代码太烂*/
        List<DeskDetailHistory> deskDetailListFoodSet = deskDetailHistoryService.getList(ckSerial, null, true);
        for (DeskDetailHistory deskDetail : deskDetailListFoodSet) {
            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(deskDetail.getFoodName());
            fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
            fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
            fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
            templateList.add(fieldTemplate);
        }
        for (DeskDetailHistory deskDetail : deskDetailHistoryList) {
            if (lastCategory.equals(deskDetail.getCategory())) {
                /*打印信息*/
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetail.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal += deskDetail.getNotNullPrice() * deskDetail.getNum();
            } else {
                if (!lastCategory.equals("")) {
                    fieldTemplateSum.setField5(fieldTemplateSum.getField5() + categoryTotal);
                }
                fieldTemplateSum = new FieldTemplate();
                fieldTemplateSum.setField5(deskDetail.getCategory() + ":");
                lastCategory = deskDetail.getCategory();
                templateList.add(fieldTemplateSum);
                categoryTotal = 0.0;
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetail.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal += deskDetail.getNotNullPrice() * deskDetail.getNum();
            }
        }
        fieldTemplateSum.setField5(szMath.ifNotNullGetString(fieldTemplateSum.getField5()) + categoryTotal);
    }
}
