package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-12-01.
 */
@RestController
public class SaunaInController {
    @Autowired
    SaunaInService saunaInService;
    @Autowired
    SaunaInHistoryService saunaInHistoryService;
    @Autowired
    TimeService timeService;
    @Autowired
    SerialService serialService;
    @Autowired
    UserService userService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    SaunaDetailService saunaDetailService;
    @Autowired
    SaunaMenuService saunaMenuService;
    @Autowired
    SaunaMenuDetailService saunaMenuDetailService;

    @RequestMapping(value = "saunaInGet")
    public List<SaunaIn> saunaInGet(@RequestBody Query query) throws Exception {
        return saunaInService.get(query);
    }
    /**
     * 判断输入的准备录入消费的手牌号是否合法
     */
    @RequestMapping(value = "checkSaunaRingIn")
    public Boolean checkSaunaRingIn(@RequestBody String ringNumber){
        return saunaInService.getByRing(ringNumber)!=null;
    }

    /**
     * 获取今日来店人数
     */
    @RequestMapping(value = "saunaTodayTotal")
    public Integer saunaTodayTotal() throws Exception {
        timeService.setNow();
        Date beginTime = timeService.getMinTime(timeService.getNow());
        Date endTime = timeService.getMaxTime(timeService.getNow());
        return saunaInService.getCountByDate(beginTime, endTime) + saunaInHistoryService.getCountByDate(beginTime, endTime);
    }
    /**
     * 开牌
     */
    @RequestMapping(value = "saunaIn")
    @Transactional(rollbackFor = Exception.class)
    public void saunaIn(@RequestBody SaunaInJson saunaInJson)throws Exception{
        timeService.setNow();
        String inCategory=saunaInJson.getInCategory();//开牌类型
        Boolean specialIn=true;//特殊开牌，例如美团，享受优惠
        if("正常".equals(inCategory)||inCategory==null){
            specialIn=false;
        }
        List<SaunaInRow> saunaInRowList=saunaInJson.getSaunaInRowList();
        Boolean group= saunaInRowList.size() > 1;//团队开牌
        String ringString="";
        if(group){
            serialService.setSaunaGroupSerial();
        }
        List<SaunaIn> saunaInList=new ArrayList<>();//需要提交的开单记录
        List<SaunaDetail> saunaDetailList=new ArrayList<>();//需要提交的项目记录
        for (SaunaInRow saunaInRow : saunaInRowList) {
            SaunaIn saunaIn=new SaunaIn();//创建消费总账
            saunaIn.setRing(saunaInRow.getRingNumber());
            if(group) {
                saunaIn.setSaunaGroupSerial(serialService.getSaunaGroupSerial());
            }
            saunaIn.setDoTime(timeService.getNow());
            saunaIn.setUserId(userService.getCurrentUser());
            SaunaMenu saunaMenu=saunaMenuService.getByName(saunaInRow.getMenu());//获取菜谱
            SaunaDetail saunaDetail=new SaunaDetail();//创建消费明细
            saunaDetail.setSaunaMenu(saunaMenu.getName());
            saunaDetail.setPrice(saunaMenu.getPrice());
            saunaIn.setTotalPrice(saunaMenu.getPrice());
            saunaIn.setInCategory(inCategory);
            if(saunaMenu.getNotNullManyPrice()&&specialIn){
                Double newPrice=saunaMenuDetailService.getByNameCategory(saunaMenu.getName(),inCategory);
                saunaDetail.setSaunaMenu(saunaMenu.getName()+"-"+inCategory);
                saunaDetail.setPrice(newPrice);
                saunaIn.setTotalPrice(newPrice);
            }
            saunaDetail.setNum(1);
            saunaDetail.setRing(saunaInRow.getRingNumber());
            saunaDetail.setUserId(userService.getCurrentUser());
            saunaDetail.setDoTime(timeService.getNow());
            saunaDetail.setMenuSign(saunaMenu.getName());
            saunaDetail.setCategory(saunaMenu.getCategory());
            saunaDetail.setRemark("开牌录入浴资");
            saunaDetail.setUnit(saunaMenu.getUnit());
            saunaDetail.setIfDiscount(saunaMenu.getIfDiscount());
            saunaDetail.setCargo(saunaMenu.getCargo());
            saunaDetail.setSaunaGroupSerial(serialService.getSaunaGroupSerial());
            saunaInList.add(saunaIn);
            saunaDetailList.add(saunaDetail);
            ringString+=saunaInRow.getRingNumber()+",";
        }
        saunaInService.add(saunaInList);
        saunaDetailService.add(saunaDetailList);
        userLogService.addUserLog("桑拿开牌:"+ringString+"|"+ifNotNullGetString(inCategory),userLogService.sauna,userLogService.saunaIn,null);
    }
}
