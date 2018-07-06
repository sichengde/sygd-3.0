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
        if (userId == null) {
            return debtHistoryMapper.getDepositByCurrencyDate(currency, beginTime, endTime);
        } else {
            return debtHistoryMapper.getDepositByUserCurrencyDate(userId, currency, beginTime, endTime);
        }
    }

    /**
     * 查询该时间段该结账操作员的退预付
     */
    public Double getTotalCancelDeposit(String userId, String currency, Date beginTime, Date endTime) {
        return debtHistoryMapper.getTotalCancelDeposit(userId, currency, beginTime, endTime);
    }

    public List<DebtHistory> getCancelDeposit(String userId, String currency, Date beginTime, Date endTime) {
        return debtHistoryMapper.getCancelDeposit(userId, currency, beginTime, endTime);
    }

    /**
     * 查询该时间段该操作员的冲账
     */
    public Double getTotalDiscountByUserTimeZone(String userId, Date beginTime, Date endTime) {
        if (userId == null) {
            return debtHistoryMapper.getTotalDiscountByTimeZone(beginTime, endTime);
        } else {
            return debtHistoryMapper.getTotalDiscountByUserTimeZone(userId, beginTime, endTime);
        }
    }

    /**
     * 查询该时间段该操作员的杂单
     */
    public Double getTotalAddByUserTimeZone(String userId, Date beginTime, Date endTime) {
        if (userId == null) {
            return debtHistoryMapper.getTotalAddByTimeZone(beginTime, endTime);
        } else {
            return debtHistoryMapper.getTotalAddByUserTimeZone(userId, beginTime, endTime);
        }
    }

    /**
     * 计算该类别在该时间段的结算款
     */
    public Double getHistoryConsume(Date beginTime, Date endTime, String pointOfSale, Boolean positive) {
        return debtHistoryMapper.getHistoryConsume(beginTime, endTime, pointOfSale, positive);
    }

    /**
     * 计算该类别在该时间段的结算款
     */
    public Double getHistoryConsume(Date beginTime, Date endTime, List<String> pointOfSale, List<String> guestSourceList, List<String> roomCategoryList) {
        return debtHistoryMapper.getHistoryConsumeRich(beginTime, endTime, pointOfSale, guestSourceList, roomCategoryList);
    }

    /**
     * 获得当日的折扣，也就是冲账
     */
    public Double getTotalDiscount(Date beginTime, Date endTime) {
        return debtHistoryMapper.getTotalDiscount(beginTime, endTime);
    }

    /**
     * 计算该营业部门在该单子下的销售情况debtService里也有，这个主要用于补打账单
     */
    public Double getTotalConsumeByPointOfSaleAndSerial(String pointOfSale, String serial) {
        return debtHistoryMapper.getTotalConsumeByPointOfSaleAndSerial(pointOfSale, serial);
    }

    /**
     * 拼接融合账务信息
     */
    public List<DebtHistory> mergeDebtHistory(List<DebtHistory> debtList,boolean group) {
        if (group) {
            List<DebtHistory> compressDebtList = new ArrayList<>();
            List<String> roomIdListCheck = new ArrayList<>();//用来检测哪些房间加入了
            Integer liveDay = 2;//统计房费天数
            for (DebtHistory debtHistoryNow : debtList) {
                if (roomIdListCheck.indexOf(debtHistoryNow.getRoomId()) > -1) {//大于-1说明房号一样
                    DebtHistory debtHistoryLast = compressDebtList.get(compressDebtList.size() - 1);
                    if (debtHistoryNow.getDescription().equals("过夜审加收房费") && debtHistoryLast.getDescription().contains("过夜审加收房费")) {//如果当前的账务是房费，并且上一条也是房费（查询的时候按照房费排列）
                        debtHistoryLast.setConsume(debtHistoryNow.getNotNullConsume() + debtHistoryLast.getNotNullConsume());
                        debtHistoryLast.setDescription("过夜审加收房费:" + String.valueOf(liveDay) + "天");
                        liveDay++;
                    } else if ("房吧".equals(debtHistoryNow.getCategory()) && debtHistoryLast.getPointOfSale().equals("房吧") && roomShopService.getShopItem(debtHistoryLast.getDescription()).equals(roomShopService.getShopItem(debtHistoryNow.getDescription()))) {
                        debtHistoryLast.setConsume(debtHistoryNow.getNotNullConsume() + debtHistoryLast.getNotNullConsume());
                        debtHistoryLast.setDescription(roomShopService.setShopNum(debtHistoryLast.getDescription(), roomShopService.getShopItem(debtHistoryNow.getDescription()), roomShopService.getShopNum(debtHistoryNow.getDescription())));
                        /*String[] itemAndMoneyCompressed = debtCompressed.getDescription().split("/");//分析消费品种
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
                        }*/
                        liveDay = 2;
                    } else {//既不能合并房费又不能合并房吧，直接加上去
                        compressDebtList.add(debtHistoryNow);
                        liveDay = 2;
                    }
                } else {//没有这个房间，新建一个
                    compressDebtList.add(debtHistoryNow);
                    roomIdListCheck.add(debtHistoryNow.getRoomId());
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
    public List<DebtHistory> debtHistoryGetByCheckOutSerial(String checkOutSerial) {
        return debtHistoryMapper.debtHistoryGetByCheckOutSerial(checkOutSerial);
    }

    /**
     * 查找除了加收房租和小时房租之外的账务，根据结账序列号，主要用于叫回账单
     */
    public List<DebtHistory> getListExcludeAddDebt(String paySerial) {
        return debtHistoryMapper.getListExcludeAddDebt(paySerial);
    }


    /**
     * 删除加收房租和小时房租的账务，根据结账序列号，主要用于叫回账单
     */
    public void deleteAddDebt(String paySerial) {
        debtHistoryMapper.deleteAddDebt(paySerial);
    }

    /**
     * 根据id号设置单位结账标志为已结
     */
    public void setPaidById(Integer id) {
        debtHistoryMapper.setPaidById(id);
    }

    /**
     * 删除中间结算在debt_history表中产生的临时平账数据
     */
    public void deleteMiddlePay() {
        debtHistoryMapper.deleteMiddlePay();
    }

    /**
     * 根据结账序列号获取押金总和
     */
    public Double getTotalDeposit(String paySerial){
        return debtHistoryMapper.getTotalDeposit(paySerial);
    }

    /**
     * 获取没有结算的单位账务
     */
    public List<DebtHistory> getListByCompanyPaid(String paySerials){
        return debtHistoryMapper.getListByCompanyPaid(paySerials);
    }

    /**
     * 获取没有结算的单位结算总金额
     */
    public Double getConsumeNotCompanyPaid(Date beginTime,Date endTime){
        return debtHistoryMapper.getConsumeNotCompanyPaid(beginTime,endTime);
    }
}
