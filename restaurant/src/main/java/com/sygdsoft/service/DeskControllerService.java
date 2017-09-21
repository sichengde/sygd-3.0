package com.sygdsoft.service;

import com.sygdsoft.model.DeskDetail;
import com.sygdsoft.model.DeskDetailHistory;
import com.sygdsoft.model.FieldTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2017-03-16.
 */
@Service
public class DeskControllerService {
    public void generateDetail(List<DeskDetail> deskDetailList,List<FieldTemplate> templateList){
        String lastCategory="";
        Double categoryTotal=0.0;
        FieldTemplate fieldTemplateSum=new FieldTemplate();
        for (DeskDetail deskDetail : deskDetailList) {
            if(lastCategory.equals(deskDetail.getCategory())) {
                /*打印信息*/
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetail.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal+=deskDetail.getNotNullPrice() * deskDetail.getNum();
            }else {
                if(!lastCategory.equals("")){
                    fieldTemplateSum.setField5(fieldTemplateSum.getField5()+categoryTotal);
                }
                fieldTemplateSum= new FieldTemplate();
                fieldTemplateSum.setField5(deskDetail.getCategory()+":");
                lastCategory=deskDetail.getCategory();
                templateList.add(fieldTemplateSum);
                categoryTotal=0.0;
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetail.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetail.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetail.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetail.getNotNullPrice() * deskDetail.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal+=deskDetail.getNotNullPrice() * deskDetail.getNum();
            }
        }
        fieldTemplateSum.setField5(fieldTemplateSum.getField5()+categoryTotal);
    }

    public void generateDetailHistory(List<DeskDetailHistory> deskDetailHistoryList, List<FieldTemplate> templateList){
        String lastCategory="";
        Double categoryTotal=0.0;
        FieldTemplate fieldTemplateSum=new FieldTemplate();
        for (DeskDetailHistory deskDetailHistory : deskDetailHistoryList) {
            if(lastCategory.equals(deskDetailHistory.getCategory())) {
            /*打印信息*/
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetailHistory.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetailHistory.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetailHistory.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetailHistory.getPrice() * deskDetailHistory.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal+=deskDetailHistory.getPrice() * deskDetailHistory.getNum();
            }else {
                if(!lastCategory.equals("")){
                    fieldTemplateSum.setField1(fieldTemplateSum.getField1()+categoryTotal);
                }
                fieldTemplateSum= new FieldTemplate();
                fieldTemplateSum.setField1(deskDetailHistory.getCategory()+":");
                lastCategory=deskDetailHistory.getCategory();
                templateList.add(fieldTemplateSum);
                categoryTotal=0.0;
                FieldTemplate fieldTemplate = new FieldTemplate();
                fieldTemplate.setField1(deskDetailHistory.getFoodName());
                fieldTemplate.setField2(ifNotNullGetString(deskDetailHistory.getPrice()));
                fieldTemplate.setField3(ifNotNullGetString(deskDetailHistory.getNum()));
                fieldTemplate.setField4(ifNotNullGetString(deskDetailHistory.getPrice() * deskDetailHistory.getNum()));
                templateList.add(fieldTemplate);
                categoryTotal+=deskDetailHistory.getPrice() * deskDetailHistory.getNum();
            }
        }
        fieldTemplateSum.setField1(fieldTemplateSum.getField1()+categoryTotal);
    }
}
