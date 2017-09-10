package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInHistoryMapper;
import com.sygdsoft.model.CheckInHistory;
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
     * 通过离店结算序列号获取当时在店的所有客人
     */
    public List<CheckInHistory> getListByCheckOutSerial(String checkOutSerial){
        return checkInHistoryMapper.getListByCheckOutSerial(checkOutSerial);
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
        String out="";
        for (CheckInHistory checkInHistory : checkInHistoryList) {
            out+=checkInHistory.getName()+",";
        }
        return out.substring(0,out.length()-1);
    }

    /**
     * 通过身份证减一次来店数据
     */
    public void minusOneNum(String cardId){
        checkInHistoryMapper.minusOneNum(cardId);
    }
}
