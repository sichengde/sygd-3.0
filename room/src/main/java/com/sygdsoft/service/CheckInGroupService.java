package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInGroupMapper;
import com.sygdsoft.model.CheckIn;
import com.sygdsoft.model.CheckInGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-05-10.
 */
@Service
@SzMapper(id = "checkInGroup")
public class CheckInGroupService extends BaseService<CheckInGroup>{
    @Autowired
    CheckInGroupMapper checkInGroupMapper;

    /**
     * 获得团队剩余没有结算的消费
     */
    public Double getNeedPay(CheckInGroup checkInGroup){
        return checkInGroup.getNotNullGroupConsume()-checkInGroup.getNotNullGroupPay();
    }

    /**
     * 删除
     */
    public void deleteByGroupAccount(String groupAccount){
        CheckInGroup checkInGroup= getByGroupAccount(groupAccount);
        checkInGroupMapper.deleteByPrimaryKey(checkInGroup);
    }

    /**
     * 获得团队开房信息
     */
    public CheckInGroup getByGroupAccount(String groupAccount) {
        if(groupAccount==null){
            return null;
        }
        CheckInGroup checkInGroupQuery = new CheckInGroup();
        checkInGroupQuery.setGroupAccount(groupAccount);
        return checkInGroupMapper.selectOne(checkInGroupQuery);
    }

}
