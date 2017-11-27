package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.CheckInGroupMapper;
import com.sygdsoft.mapper.CheckInMapper;
import com.sygdsoft.mapper.DebtMapper;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGroup;
import com.sygdsoft.model.Debt;
import com.sygdsoft.model.DebtHistory;
import com.sygdsoft.util.NullJudgement;
import com.sygdsoft.util.SzMath;
import com.sygdsoft.util.Util;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-05.
 */
@Service
@SzMapper(id = "debt")
public class DebtService extends BaseService<Debt> {
    public String deposit = "付押金";
    public String cancelDeposit = "退押金";
    public String otherConsumeIn = "杂单";
    public String otherConsumeOut = "冲账";
    public String roomShopIn = "房吧";
    public String tel = "电话费";
    public String allDayPrice = "全日房费";
    public String payMiddle = "中间结算冲账";
    @Autowired
    DebtMapper debtMapper;
    @Autowired
    CheckInMapper checkInMapper;
    @Autowired
    CheckInGroupMapper checkInGroupMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    CheckInService checkInService;
    @Autowired
    CheckInGroupService checkInGroupService;
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    RoomShopService roomShopService;
    @Autowired
    Util util;
    @Autowired
    SzMath szMath;

    /**
     * 增加一条消费(有可能出现死锁)
     */
    //TODO:有可能出现死锁，概率很小，而且出现之后后果不严重，再次执行即可，故而不考虑
    public void addDebt(Debt debt) {
        CheckIn checkIn = checkInService.getByRoomId(debt.getRoomId());
        debt.setDoTime(timeService.getNow());
        debt.setGuestSource(checkIn.getGuestSource());
        debt.setSelfAccount(checkIn.getSelfAccount());
        debt.setGroupAccount(checkIn.getGroupAccount());
        debt.setCompany(checkIn.getCompany());
        debt.setTotalConsume(checkIn.getNotNullConsume() + debt.getNotNullConsume());
        debtMapper.insert(debt);
        this.updateGuestInMoney(checkIn.getRoomId(), debt.getConsume(), debt.getDeposit());
    }

    /**
     * 增加一系列消费
     */
    public void addDebt(List<Debt> debtList) {
        for (Debt debt : debtList) {
            CheckIn checkIn = checkInService.getByRoomId(debt.getRoomId());
            debt.setDoTime(timeService.getNow());
            debt.setSelfAccount(checkIn.getSelfAccount());
            debt.setGroupAccount(checkIn.getGroupAccount());
            debt.setGuestSource(checkIn.getGuestSource());
            debt.setCompany(checkIn.getCompany());
            debt.setTotalConsume(checkIn.getNotNullConsume() + debt.getNotNullConsume());
            debtMapper.insert(debt);
            this.updateGuestInMoney(checkIn.getRoomId(), debt.getConsume(), debt.getDeposit());
        }
    }


    /**
     * 更新checkIn和checkInGroup中的消费（转房客，新增消费）
     */
    public void updateGuestInMoney(String roomId, Double consume, Double deposit) {
        CheckIn checkIn = checkInService.getByRoomId(roomId);
        checkInMapper.updateGuestInMoney(roomId);
        if (checkIn.getGroupAccount() != null) {
            CheckInGroup checkInGroup = checkInGroupService.getByGroupAccount(checkIn.getGroupAccount());
            checkInGroup.setConsume(szMath.formatTwoDecimalReturnDouble(NullJudgement.nullToZero(consume) + checkInGroup.getNotNullGroupConsume()));
            checkInGroup.setDeposit(szMath.formatTwoDecimalReturnDouble(NullJudgement.nullToZero(deposit) + checkInGroup.getNotNullGroupDeposit()));
            checkInGroupMapper.updateByPrimaryKey(checkInGroup);
        }
    }

    /**
     * 通过**获得账务数组（排序）
     */
    public List<Debt> getListByRoomId(String roomId) throws Exception {
        Query query=new Query("room_id=" + util.wrapWithBrackets(roomId));
        query.setOrderByList(new String[]{"pointOfSale", "doTime"});
        query.setOrderByListDesc(new String[]{"deposit"});
        return get(query);
    }

    /*刨除中间结算的冲账*/
    public List<Debt> getListByRoomIdPure(String roomId) throws Exception {
        Query query=new Query("room_id=" + util.wrapWithBrackets(roomId) + " and category!= \'中间结算冲账\'");
        query.setOrderByList(new String[]{"pointOfSale", "doTime"});
        query.setOrderByListDesc(new String[]{"deposit"});
        return get(query);
    }

    public List<Debt> getListByGroupAccount(String groupAccount) throws Exception {
        Query query=new Query("group_account=" + util.wrapWithBrackets(groupAccount));
        query.setOrderByList(new String[]{"roomId","pointOfSale", "doTime"});
        query.setOrderByListDesc(new String[]{"deposit"});
        return get(query);
    }

    public List<Debt> getListByBed(String bed) throws Exception {
        Query query=new Query("bed=" + util.wrapWithBrackets(bed));
        query.setOrderByList(new String[]{"pointOfSale", "doTime"});
        query.setOrderByListDesc(new String[]{"deposit"});
        return get(query);
    }

    /**
     * 通过条件获得押金数组(不排序，没退过的)
     */
    public List<Debt> getDepositListByRoomList(List<String> roomList) {
        return debtMapper.getDepositListByRoomList(util.listToStringWithPoint(roomList));
    }

    /**
     * 获得在店押金
     */
    public Double getDepositMoneyAll() {
        return debtMapper.getDepositMoneyAll();
    }

    /**
     * 获得某个币种的在店押金
     */
    public Double getDepositMoneyAll(String currency) {
        return debtMapper.getDepositMoneyAllByCurrency(currency);
    }


    public List<Debt> getDepositListByGroupAccount(String groupAccount) throws Exception {
        Query query=new Query("group_account=\' " + groupAccount+"\' and deposit>0 and remark!=\'已退\'");
        return get(query);
    }

    public List<Debt> getDepositListByBed(String bed) throws Exception {
        Query query=new Query("bed= \'"+bed+"\' and deposit>0 and remark!=\'已退\'");
        return get(query);
    }

    /**
     * 计算该营业部门在该单子下的销售情况
     */
    public Double getTotalConsumeByPointOfSaleAndSerial(String pointOfSale, String serial) {
        return debtMapper.getTotalConsumeByPointOfSaleAndSerial(pointOfSale, serial);
    }

    /**
     * 根据房号合并房费房吧条目
     */
    public List<Debt> mergeDebt(List<Debt> debtList) {
        if (otherParamService.getValueByName("房间账单合并").equals("y")) {
            List<Debt> compressDebtList = new ArrayList<>();
            List<String> roomIdListCheck = new ArrayList<>();//用来检测哪些房间加入了
            Integer liveDay = 2;//统计房费天数
            for (Debt debtAll : debtList) {
                if (roomIdListCheck.indexOf(debtAll.getRoomId()) > -1) {//大于-1说明房号一样
                    if (debtAll.getDescription().equals("过夜审加收房费") && compressDebtList.get(compressDebtList.size() - 1).getDescription().contains("过夜审加收房费")) {//如果当前的账务是房费，并且上一条也是房费（查询的时候按照房费排列）
                        Debt debt1 = compressDebtList.get(compressDebtList.size() - 1);
                        debt1.setConsume(debtAll.getNotNullConsume() + debt1.getNotNullConsume());
                        debt1.setDescription("过夜审加收房费:" + String.valueOf(liveDay) + "天");
                        liveDay++;
                    } else if ("房吧".equals(debtAll.getCategory()) && "房吧".equals(compressDebtList.get(compressDebtList.size() - 1).getCategory())) {
                        Debt debtCompressed = compressDebtList.get(compressDebtList.size() - 1);
                        debtCompressed.setConsume(debtAll.getNotNullConsume() + debtCompressed.getNotNullConsume());
                        String[] itemAndMoneyCompressed = debtCompressed.getDescription().split("/");//分析消费品种
                        String[] itemAndMoneyNew = debtAll.getDescription().split("/");//分析消费品种
                        for (int i = 0; i < itemAndMoneyNew.length; i++) {//遍历新添加进来的品种
                            String s = itemAndMoneyNew[i];//新品种
                            int j;
                            for (j = 0; j < itemAndMoneyCompressed.length; j++) {//看看之前有没有
                                String s1 = itemAndMoneyCompressed[j];
                                if (roomShopService.getShopItem(s).equals(roomShopService.getShopItem(s1))) {//同品种加上数量即可,s1是老品种
                                    debtCompressed.setDescription(roomShopService.setShopNum(debtCompressed.getDescription(), roomShopService.getShopItem(s), roomShopService.getShopNum(s)));
                                    break;
                                }
                            }
                            if (j == itemAndMoneyCompressed.length) {//说明没有重复的的品种
                                debtCompressed.setDescription(debtCompressed.getDescription() + debtAll.getDescription());
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
     * 通过历史账单恢复账务账单
     */
    public List<Debt> getByHistory(List<DebtHistory> debtHistoryList) {
        List<Debt> debtList = new ArrayList<>();
        for (DebtHistory debtHistory : debtHistoryList) {
            debtList.add(new Debt(debtHistory));
        }
        return debtList;
    }

    /**
     * 根据挂账的序列号进行删除
     */
    public void deleteByCheckOutSerial(String serial) throws Exception {
        Debt debtQuery = new Debt();
        debtQuery.setFromRoom(serial);
        this.delete(debtQuery);
    }

    /**
     * 获得该房间的总消费(用于叫回账单，重新统计consume生成checkIn)
     */
    public Double getTotalConsumeByRoomId(String roomId) {
        return debtMapper.getTotalConsumeByRoomId(roomId);
    }

    /**
     * 获得该房间的总消费(用于叫回账单，重新统计consume生成checkIn)
     */
    public Double getTotalConsumeByGroupAccount(String groupAccount) {
        return debtMapper.getTotalConsumeByGroupAccount(groupAccount);
    }

    /**
     * 获取给定账务数组的总消费
     */
    public Double getTotalConsumeByList(List<Debt> debtList) {
        Double consume = 0.0;
        for (Debt debt : debtList) {
            consume += debt.getConsume();
        }
        return consume;
    }

    /**
     * 为该房间号的账务设置公付账号（用于散客转团队和联房）
     */
    public void setGroupAccountByRoomId(String roomId, String groupAccount) throws Exception {
        /*为每一条账务设置公付账号*/
        List<Debt> debtList = getListByRoomId(roomId);
        for (Debt debt : debtList) {
            debt.setGroupAccount(groupAccount);
            update(debt);
        }
    }
}
