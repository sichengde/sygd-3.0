package com.sygdsoft.service;

import com.sygdsoft.conf.dataSource.HotelGroup;
import com.sygdsoft.mapper.VipDetailMapper;
import com.sygdsoft.model.Vip;
import com.sygdsoft.model.VipDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
@Service
@SzMapper(id = "vipDetail")
public class VipDetailService extends BaseService<VipDetail> {
    @Autowired
    VipService vipService;
    @Autowired
    UserService userService;
    @Autowired
    TimeService timeService;
    @Autowired
    VipDetailMapper vipDetailMapper;

    /**
     * 增加一条充值记录
     */
    @HotelGroup

    public void addMoneyDetail(String vipNumber, Double money, Double deserve, Double score, String currency, String pointOfSale, String category,String description) throws Exception {
        addMoneyDetail(vipNumber, money, deserve, score, currency, pointOfSale, category, description, null, null, null);
    }


    public void addMoneyDetail(String vipNumber, Double money, Double deserve, Double score, String currency, String pointOfSale, String category, String description, String selfAccount, String groupAccount, String paySerial) throws Exception {
        addMoneyDetail(vipNumber, money, deserve, score, currency, pointOfSale, category, description, selfAccount, groupAccount, paySerial,userService.getCurrentUser());
    }
    public void addMoneyDetail(String vipNumber, Double money, Double deserve, Double score, String currency, String pointOfSale, String category, String description, String selfAccount, String groupAccount, String paySerial,String userId) throws Exception {
        Vip vip = vipService.getByVipNumber(vipNumber);
        VipDetail vipDetail = new VipDetail();
        vipDetail.setVipNumber(vipNumber);
        vipDetail.setPay(money);
        vipDetail.setScore(score);
        vipDetail.setDeserve(deserve);
        vipDetail.setCurrency(currency);
        vipDetail.setCategory(category);
        vipDetail.setUserId(userId);
        vipDetail.setDoTime(timeService.getNow());
        vipDetail.setRemain(vip.getRemain());
        vipDetail.setRemainScore(vip.getScore());
        vipDetail.setPointOfSale(pointOfSale);

        vipDetail.setDescription(description);
        vipDetail.setSelfAccount(selfAccount);
        vipDetail.setGroupAccount(groupAccount);
        vipDetail.setPaySerial(paySerial);
        this.add(vipDetail);
    }

    /**
     * 根据卡号获取数组
     */
    @HotelGroup

    public List<VipDetail> getByVipNumber(String vipNumber) {
        VipDetail vipDetailQuery = new VipDetail();
        vipDetailQuery.setVipNumber(vipNumber);
        return vipDetailMapper.select(vipDetailQuery);
    }
}
