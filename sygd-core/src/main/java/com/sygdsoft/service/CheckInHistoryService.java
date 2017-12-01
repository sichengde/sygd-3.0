package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInHistoryMapper;
import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.model.CheckInHistoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkInHistory")
public class CheckInHistoryService extends BaseService<CheckInHistory>{
    @Autowired
    CheckInHistoryMapper checkInHistoryMapper;
    @Autowired
    CheckInHistoryLogService checkInHistoryLogService;

    /**
     * 通过身份证获取
     */
    public CheckInHistory getByCardId(String cardId) {
        CheckInHistory checkInGroupQuery = new CheckInHistory();
        checkInGroupQuery.setCardId(cardId);
        return checkInHistoryMapper.selectOne(checkInGroupQuery);
    }

    public List<CheckInHistory> getListByCardId(String cardId) {
        CheckInHistory checkInGroupQuery = new CheckInHistory();
        checkInGroupQuery.setCardId(cardId);
        return checkInHistoryMapper.select(checkInGroupQuery);
    }

    /**
     * 通过自付账号号获取当时在店的所有客人
     */
    public List<CheckInHistory> getListBySelfAccount(String selfAccount) {
        return checkInHistoryMapper.getListBySelfAccount(selfAccount);
    }
    /**
     * 通过离店序列号获取当时在店的所有客人
     */
    public List<CheckInHistory> getListByCheckOutSerial(String checkOutSerial){
        List<CheckInHistoryLog> checkInHistoryLogList=checkInHistoryLogService.getByCheckOutSerial(checkOutSerial);
        List<CheckInHistory> checkInHistories=new ArrayList<>();
        for (CheckInHistoryLog checkInHistoryLog : checkInHistoryLogList) {
            checkInHistories.addAll(this.getListBySelfAccount(checkInHistoryLog.getSelfAccount()));
        }
        return checkInHistories;
    }

    /**
     * 通过实体列表返回姓名字符串列表
     */
    public List<String> getNameList(List<CheckInHistory> checkInHistoryList){
        List<String> nameList=new ArrayList<>();
        for (CheckInHistory checkInHistory : checkInHistoryList) {
            nameList.add(checkInHistory.getName());
        }
        return nameList;
    }

    /**
     * 获得宾客字符串
     */
    public String listToStringName(List<CheckInHistory> checkInHistoryList){
        int iMax = checkInHistoryList.size() - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(checkInHistoryList.get(i).getName());
            if (i == iMax)
                return b.toString();
            b.append(", ");
        }
    }

    /**
     * 通过身份证减一次来店数据
     */
    public void minusOneNum(String cardId){
        checkInHistoryMapper.minusOneNum(cardId);
    }
}
