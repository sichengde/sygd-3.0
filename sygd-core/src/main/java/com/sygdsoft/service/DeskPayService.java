package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.DeskPayMapper;
import com.sygdsoft.model.DebtPay;
import com.sygdsoft.model.DeskPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
@Service
@SzMapper(id = "deskPay")
public class DeskPayService extends BaseService<DeskPay> {
    @Autowired
    DeskPayMapper deskPayMapper;

    /**
     * 根据结算序列号删除
     */
    public void deleteByCkSerial(String ckSerial) {
        DeskPay deskPayQuery = new DeskPay();
        deskPayQuery.setCkSerial(ckSerial);
        deskPayMapper.delete(deskPayQuery);
    }

    /**
     * 获得该日期该币种的消费额(餐饮,不带pos点)
     */
    public Double getPay(String userId, String currency,String pointOfSale, Date beginTime, Date endTime) {
        return deskPayMapper.getPay(userId,currency ,pointOfSale, beginTime,endTime);
    }

    /**
     * 通过结账序列号获取
     */
    public List<DeskPay> getByCkSerial(String ckSerial) {
        DeskPay deskPayQuery = new DeskPay();
        deskPayQuery.setCkSerial(ckSerial);
        return deskPayMapper.select(deskPayQuery);
    }

    /**
     * 通过时间段和销售点统计币种
     */
    public List<DeskPay> getByDatePointOfSale(Date beginTime, Date endTime, String pointOfSale) {
        return deskPayMapper.getByDatePointOfSale(beginTime, endTime, pointOfSale);
    }

    /**
     * 设置为不可用
     */
    public void setDisabledBySerial(String ckSerial) {
        deskPayMapper.setDisabledBySerial(ckSerial);
    }

    /**
     * 获取每日币种
     */
    public List<DeskPay> getConsumeEveryDay(Date beginTime, Date endTime){
        return deskPayMapper.getConsumeEveryDay(beginTime, endTime);
    }
}
