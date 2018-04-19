package com.sygdsoft.service;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.jsonModel.RoomCategoryLine;
import com.sygdsoft.jsonModel.report.RoomCategoryRow;
import com.sygdsoft.mapper.DebtIntegrationMapper;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.DebtIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-07.
 * 发生额不包括转房客
 */
@Service
@SzMapper(id = "debtIntegration")
public class DebtIntegrationService extends BaseService<DebtIntegration> {
    @Autowired
    DebtIntegrationMapper debtIntegrationMapper;
    @Autowired
    TimeService timeService;
    /**
     * 根据发生时间获得消费总额
     */
    public Double getSumConsumeByDoTime(Date beginTime, Date endTime, String pointOfSale,boolean excludeChange){
        return debtIntegrationMapper.getSumConsumeByDoTime(beginTime, endTime, pointOfSale,excludeChange);
    }

    /**
     * 根据时间获得发生额，分，房吧，零售和房费
     */
    public List<HotelParseLineRow> getSumDateLineByPointOfSale(Date beginTime, Date endTime, String pointOfSale) {
        if (pointOfSale == null) {
            return debtIntegrationMapper.getSumDateLineConsume(beginTime, endTime);
        } else {
            return debtIntegrationMapper.getSumDateLineByPointOfSale(beginTime, endTime, pointOfSale);
        }
    }

    /**
     * 通过营业部门进行聚合
     */
    public List<DebtIntegration> getList(Date beginTime,Date endTime,String userId){
        return debtIntegrationMapper.getList(beginTime, endTime,userId);
    }

    /**
     * 获得发生额线性走势
     */
    public List<HotelParseLineRow> roomConsumeChart(Date beginTime, Date endTime){
        return debtIntegrationMapper.roomConsumeChart(beginTime, endTime);
    }

    /**
     * 获得时间段内客源消费情况（只算房费）
     */
    public List<DebtIntegration> getSumRoomConsumeByDateGuestSource(Date beginTime, Date endTime) {
        return debtIntegrationMapper.getSumRoomConsumeByDateGuestSource(beginTime, endTime);
    }

    /**
     * 根据营业部门聚合消费
     */
    public List<DebtIntegration> getSumConsumeByDatePointOfSale(Date beginTime, Date endTime) {
        return debtIntegrationMapper.getSumConsumeByDatePointOfSale(beginTime, endTime);
    }

    /**
     * 根据操作员币种聚合押金
     */
    public List<DebtIntegration> getSumDepositByDate(Date beginTime, Date endTime) {
        return debtIntegrationMapper.getSumDepositByDate(beginTime, endTime);
    }

    /**
     * 根据操作员，币种，时间，算出单退押金总和
     */
    public Double getSumCancelDeposit(String userId, String currency, Date beginTime, Date endTime) {
        return debtIntegrationMapper.getDepositByUserCurrencyDate(userId, currency, beginTime, endTime);
    }

    /**
     * 根据操作员，币种，时间，算出单退押金明细（没用上）
     */
    List<DebtIntegration> getDepositList(String userId, String currency, Date beginTime, Date endTime) {
        return debtIntegrationMapper.getDepositList(userId, currency, beginTime, endTime);
    }

    /**
     * 获得时间段内该房类走势
     */
    public List<RoomCategoryLine> parseRoomCategoryDebtLine(Date beginTime, Date endTime, String category) {
        return debtIntegrationMapper.parseRoomCategoryDebtLine(beginTime, endTime, category);
    }

    /**
     * 获得某个时间点前的在店预付
     */
    public Double getSumDepositByEndTime(Date beginTime,Date endTime,String userId){
        return debtIntegrationMapper.getSumDepositByEndTime(beginTime, endTime, userId);
    }

    /**
     * 获取每日出租房数
     */
    public List<DebtIntegration> getDailyCount(Date beginTime,Date endTime,boolean fixDate)throws Exception{
        int fixDateInt=0;
        if(fixDate){
            fixDateInt=1;
        }
        return debtIntegrationMapper.getDailyCount(beginTime,endTime,fixDateInt);
    }
}
