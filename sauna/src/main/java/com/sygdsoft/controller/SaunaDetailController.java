package com.sygdsoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sygdsoft.jsonModel.PrintMessage;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaDetailController {
    @Autowired
    SaunaDetailService saunaDetailService;
    @Autowired
    SaunaInService saunaInService;
    @Autowired
    SaunaMenuService saunaMenuService;
    @Autowired
    UserService userService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    TimeService timeService;
    @Autowired
    SaunaMenuDetailService saunaMenuDetailService;
    @Autowired
    HotelService hotelService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    OtherParamService otherParamService;

    @RequestMapping(value = "saunaDetailGet")
    public List<SaunaDetail> saunaDetailGet(@RequestBody Query query) throws Exception {
        return saunaDetailService.get(query);
    }

    /**
     * 点消费(需要生成单子)
     */
    @RequestMapping(value = "saunaDetailIn")
    @Transactional(rollbackFor = Exception.class)
    public void saunaDetailIn(@RequestBody List<SaunaDetail> saunaDetailList) throws Exception {
        timeService.setNow();
        /*通过第一个消费获取账单信息*/
        SaunaDetail firstSaunaDetail=saunaDetailList.get(0);
        SaunaIn saunaIn = saunaInService.getByRing(firstSaunaDetail.getRing());
        String inCategory = saunaIn.getInCategory();
        Boolean specialIn = true;//特殊开牌，例如美团，享受优惠
        if ("正常".equals(inCategory) || inCategory == null) {
            specialIn = false;
        }
        /*累计入账金额*/
        Double totalConsume = 0.0;
        /*打印对象*/
        List<FieldTemplate> fieldTemplateList = new ArrayList<>();
        /*正常账单*/
        for (SaunaDetail saunaDetail : saunaDetailList) {
            SaunaMenu saunaMenu = saunaMenuService.getByName(saunaDetail.getMenuSign());
            if (saunaMenu.getNotNullManyPrice() && specialIn) {
                Double newPrice = saunaMenuDetailService.getByNameCategory(saunaMenu.getName(), inCategory);
                saunaDetail.setSaunaMenu(saunaMenu.getName() + "-" + inCategory);
                saunaDetail.setPrice(newPrice);
                totalConsume += newPrice;
            } else {
                saunaDetail.setPrice(saunaMenu.getPrice());
                saunaDetail.setSaunaMenu(saunaMenu.getName());
                totalConsume += saunaMenu.getPrice();
            }
            saunaDetail.setUserId(userService.getCurrentUser());
            saunaDetail.setDoTime(timeService.getNow());
            saunaDetail.setCategory(saunaMenu.getCategory());
            saunaDetail.setUnit(saunaMenu.getUnit());
            saunaDetail.setIfDiscount(saunaMenu.getIfDiscount());
            saunaDetail.setCargo(saunaMenu.getCargo());
            saunaDetail.setSaunaGroupSerial(saunaIn.getSaunaGroupSerial());

            FieldTemplate fieldTemplate = new FieldTemplate();
            fieldTemplate.setField1(saunaDetail.getSaunaMenu());
            fieldTemplate.setField2(String.valueOf(saunaDetail.getNum()));
            fieldTemplate.setField3(saunaDetail.getSaunaUser());
            fieldTemplateList.add(fieldTemplate);
        }
        saunaDetailService.add(saunaDetailList);
        saunaInService.addConsume(saunaIn.getRing(), totalConsume);
        userLogService.addUserLog("消费录入:"+saunaIn.getRing(),userLogService.sauna,userLogService.saunaDetail,firstSaunaDetail.getSaunaUser(),null);
        /*打印
        * param
        * 1.酒店名称
        * 2.手牌号
        * field
        * 1.项目
        * 2.数量
        * 3.技师
        * */
        String printer = otherParamService.getValueByName("小票打印机");
        if (printer != null) {//有打印机名才会去打印
            List<String> paramList = new ArrayList<>();
            paramList.add(otherParamService.getValueByName("酒店名称"));
            paramList.add(saunaIn.getRing());
            PrintMessage printMessage = new PrintMessage(fieldTemplateList, otherParamService.getValueByName("小票打印机"), "saunaDetail", paramList);
            ObjectMapper mapper = new ObjectMapper();
            hotelService.postJSON("http://" + request.getRemoteAddr() + ":8080/printClient", mapper.writeValueAsString(printMessage));
        }
    }
}
