package com.sygdsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.json.VipRecharge;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Vip;
import com.sygdsoft.model.VipDetail;
import com.sygdsoft.model.VipDetailHistory;
import com.sygdsoft.model.VipHistory;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;
import static com.sygdsoft.util.NullJudgement.nullToZero;

/**
 * Created by 舒展 on 2016-05-09.
 */
@RestController
public class VipController {
    @Autowired
    VipService vipService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    VipDetailService vipDetailService;
    @Autowired
    VipCategoryService vipCategoryService;
    @Autowired
    Util util;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    VipHistoryService vipHistoryService;
    @Autowired
    VipDetailHistoryService vipDetailHistoryService;
    @Autowired
    DebtPayService debtService;
    @Autowired
    RegisterService registerService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "vipUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void vipUpdate(@RequestBody List<Vip> vipList) throws Exception {
        if (vipList.size() > 1) {
            if (vipList.get(0).getId().equals(vipList.get(vipList.size() / 2).getId())) {
                timeService.setNow();
                String s = userLogService.parseListDeference(vipList);
                userLogService.addUserLog(s, userLogService.vip, userLogService.update,null);
                vipService.update(vipList.subList(0, vipList.size() / 2));
                return;
            }
        }
        vipService.update(vipList);
    }

    /**
     * 开卡
     *
     * @param vip
     * @throws Exception
     */
    @RequestMapping(value = "vipAdd")
    @Transactional(rollbackFor = Exception.class)
    public Integer vipAdd(@RequestBody Vip vip) throws Exception {
        timeService.setNow();
        userLogService.addUserLog("卡号:" + vip.getVipNumber() + "金额:" + vip.getRemain(), userLogService.vip, userLogService.vipCreate,vip.getVipNumber());
        /*身份证转生日*/
        String idCard=vip.getCardId();
        if(idCard!=null) {
            if (idCard.length() == 18) {
                vip.setBirthdayTime(timeService.stringToDateShort(idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14)));
            }
        }
        String currency=vip.getCurrencyPost().getCurrency();
        String currencyAdd=vip.getCurrencyPost().getCurrencyAdd();
        Double realMoney=vip.getRemain();//实际充值的金额，因为计表后该值将会被抵用金额覆盖，所以在此先保留;
        String[] params = new String[]{otherParamService.getValueByName("酒店名称"), vip.getVipNumber(), vip.getCategory(), vip.getCardId(), vip.getName(), vip.getPhone(), String.valueOf(vip.getRemain()), ifNotNullGetString(vip.getDeserve()), currency+"/"+szMath.ifNotNullGetString(currencyAdd),timeService.dateToStringShort(vip.getRemainTime()),vip.getWorkCompany(),vip.getRemark()};
        if("y".equals(otherParamService.getValueByName("充值算积分"))){
            vip.setScore(vip.getRemain());
        }
        if (vip.getDeserve() != null) {
            vip.setRemain(vip.getDeserve());
        }
        vipService.add(vip);
        if (vip.getRemain() != null) {//有金额的话就增加一条充值记录
            /*增加一条账务明细*/
            vipDetailService.addMoneyDetail(vip.getVipNumber(), realMoney, vip.getDeserve(), currency,vip.getPointOfSale(),vipService.KKCZ);
        }
        debtService.parseCurrency(currency,currencyAdd,realMoney,null,null,"会员发卡充值",null,null,null );
        /*判断币种，这里允许挂单位账*/
        return reportService.generateReport(null, params, "vipAdd", "pdf");
    }

    /**
     * 注销
     */
    @RequestMapping(value = "vipCancel")
    @Transactional(rollbackFor = Exception.class)
    public void vipCancel(@RequestBody String vipNumber) throws Exception {
        timeService.setNow();
        Vip vip = vipService.getByVipNumber(vipNumber);
        List<VipDetail> vipDetailList = vipDetailService.getByVipNumber(vipNumber);
        vipService.delete(vip);
        vipDetailService.delete(vipDetailList);
        VipHistory vipHistory = new VipHistory(vip);
        vipHistory.setDoneTime(timeService.getNow());
        vipHistoryService.add(vipHistory);
        List<VipDetailHistory> vipDetailHistoryList = new ArrayList<>();
        for (VipDetail vipDetail : vipDetailList) {
            vipDetailHistoryList.add(new VipDetailHistory(vipDetail, vipHistory.getId()));
        }
        vipDetailHistoryService.add(vipDetailHistoryList);
        userLogService.addUserLog("注销:" + vipNumber, userLogService.vip, userLogService.cancel,vipNumber);
    }

    /**
     * 充值
     * @return
     */
    @RequestMapping(value = "vipRecharge")
    @Transactional(rollbackFor = Exception.class)
    public Integer vipRecharge(@RequestBody VipRecharge vipRecharge) throws Exception {
        /*安全校验*/
        if(!this.registerService.securityStr.remove(vipRecharge.getToken())){
            throw new Exception("充值失败");
        }
        timeService.setNow();
        String vipNumber = vipRecharge.getVipNumber();
        String pointOfSale = vipRecharge.getPointOfSale();
        Double money = vipRecharge.getMoney();
        Double deserve = vipRecharge.getDeserve();
        String currency=vipRecharge.getCurrencyPost().getCurrency();
        String currencyAdd=vipRecharge.getCurrencyPost().getCurrencyAdd();
        boolean withScore="y".equals(otherParamService.getValueByName("充值算积分"));
        vipService.updateVipRemain(vipNumber, deserve,money,withScore);
        /*增加一条账务明细*/
        vipDetailService.addMoneyDetail(vipNumber, money, deserve, currency,pointOfSale);
        userLogService.addUserLog("卡号:" + vipNumber + " 充值:" + money+" 抵用: "+deserve+" 币种: "+currency+"/"+currencyAdd, userLogService.vip, userLogService.recharge,vipNumber);
        String[] param = new String[]{otherParamService.getValueByName("酒店名称"), vipNumber, String.valueOf(money), ifNotNullGetString(deserve), currency+"/"+currencyAdd};
        debtService.parseCurrency(currency,currencyAdd,money,null,null,"会员充值",null,null ,null);
        return reportService.generateReport(null, param, "vipRecharge", "pdf");
    }

    /**
     * 退款
     */
    @RequestMapping(value = "vipRefund")
    @Transactional(rollbackFor = Exception.class)
    public Integer vipRefund(@RequestBody List<String> params) throws Exception {
        timeService.setNow();
        String vipNumber = params.get(0);
        Double money = Double.valueOf(params.get(1));
        Double deserve = Double.valueOf(params.get(2));
        String currency = params.get(3);
        String pointOfSale = params.get(4);
        boolean withScore="y".equals(otherParamService.getValueByName("充值算积分"));
        vipService.updateVipRemain(vipNumber, -deserve,-money,withScore);
        vipDetailService.addRefundDetail(vipNumber, -money,-deserve,currency,pointOfSale);
        userLogService.addUserLog("卡号:" + vipNumber + " 退款:" + money+" 抵用: "+deserve+" 币种: "+currency, userLogService.vip, userLogService.refund,vipNumber);
        String[] param = new String[]{otherParamService.getValueByName("酒店名称"), vipNumber, String.valueOf(money), ifNotNullGetString(deserve), currency};
        return reportService.generateReport(null, param, "vipRefund", "pdf");
    }

    @RequestMapping(value = "vipGet")
    public List<Vip> vipGet(@RequestBody Query query) throws Exception {
        return vipService.get(query);
    }
}
