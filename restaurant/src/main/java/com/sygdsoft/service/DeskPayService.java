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
    public Double getDeskMoneyByCurrencyDateUser(String userId, String currency, Date beginTime, Date endTime) {
        if (userId == null) {
            return deskPayMapper.getDeskMoneyByCurrencyDate(beginTime, endTime, currency);
        } else {
            return deskPayMapper.getDeskMoneyByCurrencyDateUser(userId, beginTime, endTime, currency);
        }
    }

    /**
     * 获得该日期该币种的消费额(餐饮,不带销售员，带pos点（一级营业部门）)
     */
    public Double getDeskMoneyByCurrencyDatePointOfSale(String pointOfSale, String currency, Date beginTime, Date endTime) {
        return deskPayMapper.getDeskMoneyByCurrencyDatePointOfSale(pointOfSale, beginTime, endTime, currency);
    }

    /**
     * 获得该日期该币种的数组(餐饮,不带销售员，带pos点（一级营业部门）)
     */
    public List<DeskPay> getByCurrencyDatePointOfSale(String pointOfSale, String currency, Date beginTime, Date endTime) {
        return deskPayMapper.getByCurrencyDatePointOfSale(pointOfSale, beginTime, endTime, currency);
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
}
