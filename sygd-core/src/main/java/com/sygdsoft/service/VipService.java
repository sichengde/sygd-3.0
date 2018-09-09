package com.sygdsoft.service;

import com.sygdsoft.conf.dataSource.HotelGroup;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.VipMapper;
import com.sygdsoft.model.Vip;
import com.sygdsoft.model.VipDetail;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/9 0009.
 * 会员服务
 */
@Service
@SzMapper(id = "vip")
public class VipService extends BaseService<Vip> {
    public String HY = "会员余额结账";//会员余额结账
    public String XJ = "会员现金结账";
    public String CZ = "会员充值";
    public String KKCZ = "会员开卡充值";
    public String TK = "会员退款";
    public String KS = "客史";
    public String JH = "会员结账叫回";
    @Autowired
    VipMapper vipMapper;
    @Autowired
    PointOfSaleService pointOfSaleService;
    @Autowired
    TimeService timeService;
    @Autowired
    UserService userService;
    @Autowired
    SerialService serialService;
    @Autowired
    VipDetailService vipDetailService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    Util util;

    /**
     * 注销(被弃用)
     */
    public void cancel(String vipNumber) {
        vipMapper.cancel(vipNumber);
    }

    /**
     * 通过卡号获取对象
     */
    public Vip getByVipNumber(String vipNumber) {
        Vip vipQuery = new Vip();
        vipQuery.setVipNumber(vipNumber);
        return vipMapper.selectOne(vipQuery);
    }

    /**
     * 更新会员余额
     */
    public void updateVipRemain(String vipNumber, Double deserve,Double real,boolean withScore) {
        vipMapper.updateVipRemain(vipNumber, deserve,real,withScore);
    }

    /**
     * 更新会员积分
     */
    public void updateVipScore(String vipNumber, Double score) {
        vipMapper.updateVipScore(vipNumber, score);
    }

    /**
     * 开房时冻结一部分押金，押金为负则是解冻
     */
    public void depositByVip(String vipNumber, Double deposit) {
        vipMapper.depositByVip(vipNumber, deposit);
    }

    /**
     * 结账时使用用会员币种（离店，商品零售）
     */
    public String vipPay(String vipNumber, String payCategory, Double money, String description, String selfAccount, String groupAccount,String paySerial,String pointOfSale) throws Exception {
        Vip vip=getByVipNumber(vipNumber);
        VipDetail vipDetail = new VipDetail();
        vipDetail.setVipNumber(vipNumber);
        vipDetail.setPointOfSale(pointOfSale);
        vipDetail.setConsume(money);
        if(money>0) {
            vipDetail.setCategory(HY);
        }else {
            vipDetail.setCategory(JH);
        }
        vipDetail.setDescription("会员" + payCategory + description);
        vipDetail.setCurrency("会员");
        vipDetail.setSelfAccount(selfAccount);
        vipDetail.setGroupAccount(groupAccount);
        vipDetail.setPaySerial(paySerial);
        vipDetail.setUserId(userService.getCurrentUser());
        vipDetail.setDoTime(timeService.getNow());
        vipDetail.setRemain(vip.getRemain()-money);
        vipDetailService.add(vipDetail);
        String remainMessage="记会员至:"+vipNumber;
        if (payCategory.contains("积分")) {
            if(vip.getNotNullScore()<money / Double.valueOf(otherParamService.getValueByName("积分比率"))){
                throw new Exception("积分不足,当前积分："+vip.getScore()+" 所需积分:"+money / Double.valueOf(otherParamService.getValueByName("积分比率")));
            }
            remainMessage+=",积分:" + (vip.getScore()-money);
            updateVipScore(vipNumber, -money / Double.valueOf(otherParamService.getValueByName("积分比率")));
        } else {
            if(vip.getRemain()<money){
                throw new Exception("余额不足,当前余额:"+vip.getRemain()+" 支付金额:"+money);
            }
            remainMessage+=",余额:" + (vip.getRemain()-money);
            updateVipRemain(vipNumber, -money,-money,false);
        }
        return remainMessage;
    }
}
