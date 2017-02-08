package com.sygdsoft.service;

import com.sygdsoft.mapper.GroupIntegrationMapper;
import com.sygdsoft.model.GroupIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-08.
 */
@Service
@SzMapper(id = "groupIntegration")
public class GroupIntegrationService extends BaseService<GroupIntegration>{
    @Autowired
    GroupIntegrationMapper groupIntegrationMapper;
    /**
     * 获得一段时间内的接待团队数量
     */
    public Integer getSumByDate(Date beginTime,Date endTime){
        return groupIntegrationMapper.getSumByDate(beginTime,endTime);
    }
}
