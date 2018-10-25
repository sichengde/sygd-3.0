package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.VipMapper;
import com.sygdsoft.model.Vip;
import com.sygdsoft.model.VipCategory;
import com.sygdsoft.model.VipDetail;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/6/9 0009.
 * 会员服务
 */
@Service
@SzMapper(id = "vip")
public class VipService extends BaseService<Vip> {
    public String JF = "积分变更";
    public String YE = "余额变更";
    public String HY = "会员余额结账";//会员余额结账
    public String XJ = "会员现金结账";
    public String CZ = "会员充值";
    public String KKCZ = "会员开卡充值";
    public String TK = "会员退款";
    public String JFBD = "手动变更积分";
    public String LJJF = "消费积分累计";
    public String HYR = "会员日积分";
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
    @Autowired
    VipCategoryService vipCategoryService;

    /**
     * 同时获得协议
     */
    @Override
    public List<Vip> get(Query query) throws Exception {
        return vipMapper.get(query);
    }

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
    public void updateVipRemain(String vipNumber, Double deserve, Double real) {
        vipMapper.updateVipRemain(vipNumber, deserve, real);
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
    public String vipPay(String vipNumber, String payCategory, Double money, String description, String selfAccount, String groupAccount, String paySerial, String pointOfSale) throws Exception {
        Vip vip = getByVipNumber(vipNumber);
        VipDetail vipDetail = new VipDetail();
        vipDetail.setVipNumber(vipNumber);
        vipDetail.setPointOfSale(pointOfSale);
        vipDetail.setConsume(money);
        if (money > 0) {
            vipDetail.setDescription(HY);
        } else {
            vipDetail.setDescription(JH);
        }
        vipDetail.setDescription("会员" + payCategory + description);
        vipDetail.setCurrency("会员");
        vipDetail.setSelfAccount(selfAccount);
        vipDetail.setGroupAccount(groupAccount);
        vipDetail.setPaySerial(paySerial);
        vipDetail.setUserId(userService.getCurrentUser());
        vipDetail.setDoTime(timeService.getNow());
        vipDetail.setRemain(vip.getNotNullVipRemain() - money);
        String remainMessage = "记会员至:" + vipNumber;
        VipCategory vipCategory = vipCategoryService.getByCategory(vip.getCategory());
        if (payCategory.contains("积分")) {
            Double scoreRate;
            if (vipCategory == null || vipCategory.getScoreRate() == null) {
                throw new Exception("选择积分结算，但没有在系统维护中定义积分比率");
            } else {
                scoreRate = vipCategory.getScoreRate();
            }
            if (vip.getNotNullScore() < money / scoreRate) {
                throw new Exception("积分不足,当前积分：" + vip.getScore() + " 所需积分:" + money / scoreRate);
            }
            remainMessage += ",积分:" + (vip.getScore() - money / scoreRate);
            updateVipScore(vipNumber, -money / scoreRate);
            vipDetail.setCategory(JF);
            vipDetail.setScore(-money / scoreRate);
            vipDetail.setRemain(vip.getNotNullVipRemain());
            vipDetail.setRemainScore(vip.getScore() - money / scoreRate);
        } else {
            vipDetail.setCategory(YE);
            vipDetail.setPay(-money);
            vipDetail.setRemain(vip.getNotNullVipRemain() - money);
            vipDetail.setRemainScore(vip.getNotNullScore());
            if (vip.getRemain() < money) {
                throw new Exception("余额不足,当前余额:" + vip.getRemain() + " 支付金额:" + money);
            }
            remainMessage += ",余额:" + (vip.getRemain() - money);
            updateVipRemain(vipNumber, -money, -money);
        }
        vipDetailService.add(vipDetail);
        return remainMessage;
    }
}
