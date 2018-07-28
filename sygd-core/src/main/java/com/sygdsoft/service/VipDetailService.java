package com.sygdsoft.service;

import com.sygdsoft.conf.dataSource.HotelGroup;
import com.sygdsoft.mapper.VipDetailMapper;
import com.sygdsoft.model.Vip;
import com.sygdsoft.model.VipDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
@Service
@SzMapper(id = "vipDetail")
public class VipDetailService extends VipBaserService<VipDetail>{
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

    public void addMoneyDetail(String vipNumber,Double money,Double deserve,String currency,String pointOfSale) throws Exception {
        Vip vip=vipService.getByVipNumber(vipNumber);
        VipDetail vipDetail = new VipDetail();
        vipDetail.setVipNumber(vipNumber);
        vipDetail.setPay(money);
        vipDetail.setDeserve(deserve);
        vipDetail.setCurrency(currency);
        vipDetail.setCategory(vipService.CZ);
        vipDetail.setUserId(userService.getCurrentUser());
        vipDetail.setDoTime(timeService.getNow());
        vipDetail.setRemain(vip.getRemain());
        vipDetail.setPointOfSale(pointOfSale);
        this.add(vipDetail);
    }

    /**
     * 增加一条退款记录
     */
    @HotelGroup

    public void addRefundDetail(String vipNumber,Double money,Double deserve,String currency,String pointOfSale) throws Exception {
        Vip vip=vipService.getByVipNumber(vipNumber);
        VipDetail vipDetail = new VipDetail();
        vipDetail.setVipNumber(vipNumber);
        vipDetail.setPay(money);
        vipDetail.setDeserve(deserve);
        vipDetail.setCurrency(currency);
        vipDetail.setCategory(vipService.TK);
        vipDetail.setUserId(userService.getCurrentUser());
        vipDetail.setDoTime(timeService.getNow());
        vipDetail.setRemain(vip.getRemain());
        vipDetail.setPointOfSale(pointOfSale);
        this.add(vipDetail);
    }

    /**
     * 根据结账序列号删除
     */
    @HotelGroup

    public void deleteBySerial(String paySerial) throws Exception {
        VipDetail vipDetail=new VipDetail();
        vipDetail.setPaySerial(paySerial);
        this.delete(vipDetail);
    }

    /**
     * 根据卡号获取数组
     */
    @HotelGroup

    public List<VipDetail> getByVipNumber(String vipNumber){
        VipDetail vipDetailQuery=new VipDetail();
        vipDetailQuery.setVipNumber(vipNumber);
        return vipDetailMapper.select(vipDetailQuery);
    }
}
