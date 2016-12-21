package com.sygdsoft.service;

import com.sygdsoft.mapper.DebtHistoryMapper;
import com.sygdsoft.model.DebtHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-06-24.
 */
@Service
@SzMapper(id = "debtHistory")
public class DebtHistoryService extends BaseService<DebtHistory> {
    @Autowired
    DebtHistoryMapper debtHistoryMapper;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    RoomShopService roomShopService;

    /**
     * 查询该时间段该操作员的预付
     */
    public Double getTotalDepositByUserCurrencyDate(String userId, String currency, Date beginTime, Date endTime) {
        if(userId==null){
            return debtHistoryMapper.getDepositByCurrencyDate(currency, beginTime, endTime);
        }else {
            return debtHistoryMapper.getDepositByUserCurrencyDate(userId, currency, beginTime, endTime);
        }
    }

    /**
     * 查询该时间段该操作员的退预付
     */
    public Double getTotalCancelDepositByUserCurrencyDate(String userId, String currency, Date beginTime, Date endTime) {
        if(userId==null){
            return debtHistoryMapper.getTotalCancelDepositByCurrencyDate(currency, beginTime, endTime);
        }else {
            return debtHistoryMapper.getTotalCancelDepositByUserCurrencyDate(userId, currency, beginTime, endTime);
        }
    }

    /**
     * 查询该时间段该操作员的冲账
     */
    public Double getTotalDiscountByUserTimeZone(String userId, Date beginTime, Date endTime) {
        if(userId==null){
            return debtHistoryMapper.getTotalDiscountByTimeZone(beginTime, endTime);
        }else {
            return debtHistoryMapper.getTotalDiscountByUserTimeZone(userId, beginTime, endTime);
        }
    }

    /**
     * 查询该时间段该操作员的杂单
     */
    public Double getTotalAddByUserTimeZone(String userId, Date beginTime, Date endTime) {
        if(userId==null){
            return debtHistoryMapper.getTotalAddByTimeZone(beginTime, endTime);
        }else {
            return debtHistoryMapper.getTotalAddByUserTimeZone(userId, beginTime, endTime);
        }
    }

    /**
     * 计算该类别在该时间段的结算款
     */
    public Double getHistoryConsume(Date beginTime, Date endTime, String pointOfSale) {
        return debtHistoryMapper.getHistoryConsume(beginTime, endTime, pointOfSale);
    }

    /**
     * 获得当日的折扣，也就是冲账
     */
    public Double getTotalDiscount(Date beginTime, Date endTime) {
        return debtHistoryMapper.getTotalDiscount(beginTime, endTime);
    }

    /**
     * 获得该时间段内的每日客房发生额
     */
    public List<DebtHistory> getConsumePerDay(Date beginTime, Date endTime) {
        return debtHistoryMapper.getConsumePerDay(beginTime, endTime);
    }

    /**
     * 拼接融合账务信息
     */
    public List<DebtHistory> mergeDebtHistory(List<DebtHistory> debtList) {
        if (otherParamService.getValueByName("房间账单合并").equals("y")) {
            List<DebtHistory> compressDebtList = new ArrayList<>();
            List<String> roomIdListCheck = new ArrayList<>();//用来检测哪些房间加入了
            Integer liveDay = 2;//统计房费天数
            for (DebtHistory debtAll : debtList) {
                if (roomIdListCheck.indexOf(debtAll.getRoomId()) > -1) {//大于-1说明房号一样
                    if (debtAll.getDescription().equals("过夜审加收房费") && compressDebtList.get(compressDebtList.size() - 1).getDescription().contains("过夜审加收房费")) {//如果当前的账务是房费，并且上一条也是房费（查询的时候按照房费排列）
                        DebtHistory debt1 = compressDebtList.get(compressDebtList.size() - 1);
                        debt1.setConsume(debtAll.getNotNullConsume() + debt1.getNotNullConsume());
                        debt1.setDescription("过夜审加收房费:" + String.valueOf(liveDay) + "天");
                        liveDay++;
                    } else if (debtAll.getPointOfSale().equals("房吧") && compressDebtList.get(compressDebtList.size() - 1).getPointOfSale().equals("房吧")) {
                        DebtHistory debtCompressed = compressDebtList.get(compressDebtList.size() - 1);
                        debtCompressed.setConsume(debtAll.getNotNullConsume() + debtCompressed.getNotNullConsume());
                        String[] itemAndMoneyCompressed = debtCompressed.getDescription().split("/");//分析消费品种
                        String[] itemAndMoneyNew = debtAll.getDescription().split("/");//分析消费品种
                        for (int i = 0; i < itemAndMoneyNew.length; i++) {//遍历新添加进来的品种
                            int j;
                            String s = itemAndMoneyNew[i];//新品种
                            for (j = 0; j < itemAndMoneyCompressed.length; j++) {//看看之前有没有
                                String s1 = itemAndMoneyCompressed[j];
                                if (roomShopService.getShopItem(s).equals(roomShopService.getShopItem(s1))) {//同品种加上数量即可,s1是老品种
                                    debtCompressed.setDescription(roomShopService.setShopNum(debtCompressed.getDescription(), roomShopService.getShopItem(s), roomShopService.getShopNum(s)));
                                    break;
                                }
                            }
                            if (j == itemAndMoneyCompressed.length) {//说明没有重复的的品种
                                debtCompressed.setDescription(debtCompressed.getDescription() + s);
                            }
                        }
                        liveDay = 2;
                    } else {//既不能合并房费又不能合并房吧，直接加上去
                        compressDebtList.add(debtAll);
                        liveDay = 2;
                    }
                } else {//没有这个房间，新建一个
                    compressDebtList.add(debtAll);
                    roomIdListCheck.add(debtAll.getRoomId());
                    liveDay = 2;
                }
            }
            return compressDebtList;
        } else {
            return debtList;
        }
    }

    /**
     * 通过结账序号获取历史账务
     */
    public List<DebtHistory> getListByPaySerial(String paySerial) {
        DebtHistory debtHistoryQuery = new DebtHistory();
        debtHistoryQuery.setPaySerial(paySerial);
        return debtHistoryMapper.select(debtHistoryQuery);
    }
    /**
     * 通过离店序号获取历史账务
     */
    public List<DebtHistory> debtHistoryGetByCheckOutSerial(String checkOutSerial){
        return debtHistoryMapper.debtHistoryGetByCheckOutSerial(checkOutSerial);
    }

    /**
     * 计算该营业部门在该单子下的销售情况debtService里也有，这个主要用于补打账单
     */
    public Double getTotalConsumeByPointOfSaleAndSerial(String pointOfSale, String serial) {
        return debtHistoryMapper.getTotalConsumeByPointOfSaleAndSerial(pointOfSale, serial);
    }

    /**
     * 查找除了加收房租和小时房租之外的账务，根据结账序列号，主要用于叫回账单
     */
    public List<DebtHistory> getListExcludeAddDebt(String paySerial) {
        return debtHistoryMapper.getListExcludeAddDebt(paySerial);
    }

    /**
     * 获得加收房租和小时房租的总金额，根据结账序列号，主要用于叫回账单
     */
    public Double selectTotalConsumeDebtAdd(String paySerial) {
        return debtHistoryMapper.selectTotalConsumeDebtAdd(paySerial);
    }

    /**
     * 删除加收房租和小时房租的账务，根据结账序列号，主要用于叫回账单
     */
    public void deleteAddDebt(String paySerial) {
        debtHistoryMapper.deleteAddDebt(paySerial);
    }
}
