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
 */
@Service
@SzMapper(id = "debtIntegration")
public class DebtIntegrationService extends BaseService<DebtIntegration> {
    @Autowired
    DebtIntegrationMapper debtIntegrationMapper;

    /**
     * 根据时间获得房类销售分析
     */
    public List<RoomCategoryRow> parseRoomCategoryDebt(Date beginTime, Date endTime) {
        return debtIntegrationMapper.parseRoomCategoryDebt(beginTime, endTime);
    }

    /**
     * 获得时间段内该房类走势
     */
    public List<RoomCategoryLine> parseRoomCategoryDebtLine(Date beginTime, Date endTime, String category) {
        return debtIntegrationMapper.parseRoomCategoryDebtLine(beginTime, endTime, category);
    }

    /**
     * 根据时间获得发生额，分，房吧，零售和房费
     */
    public String getSumByPointOfSale(Date beginTime, Date endTime, String pointOfSale) {
        if (pointOfSale == null) {
            return debtIntegrationMapper.getSumConsume(beginTime, endTime);
        } else {
            return debtIntegrationMapper.getSumByPointOfSale(beginTime, endTime, pointOfSale);
        }
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
     * 根据时间获得全日房费总数
     */
    public Double getSumAllDayConsumeByDate(Date beginTime, Date endTime) {
        return debtIntegrationMapper.getSumAllDayConsumeByDate(beginTime, endTime);
    }

    /**
     * 获得时间段内客源消费情况（只算房费）
     */
    public List<DebtIntegration> getSumRoomConsumeByDateGuestSource( Date beginTime, Date endTime) {
        return debtIntegrationMapper.getSumRoomConsumeByDateGuestSource( beginTime, endTime);
    }

    /**
     * 根据营业部门聚合消费
     */
    public List<DebtIntegration> getSumConsumeByDatePointOfSale( Date beginTime,  Date endTime){
        return debtIntegrationMapper.getSumConsumeByDatePointOfSale(beginTime, endTime);
    }
}
