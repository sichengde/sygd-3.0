package com.sygdsoft.service;

import com.sygdsoft.mapper.GroupIntegrationMapper;
import com.sygdsoft.mapper.GuestIntegrationMapper;
import com.sygdsoft.model.GuestIntegration;
import com.sygdsoft.model.OtherParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2017-02-09.
 */
@Service
@SzMapper(id = "guestIntegration")
public class GuestIntegrationService extends BaseService<GuestIntegration>{
    @Autowired
    OtherParamService otherParamService;
    @Autowired
    GuestIntegrationMapper guestIntegrationMapper;

    /**
     * 获取本地客人总数
     */
    public String getLocalGuestSum() throws Exception {
        String firstNum= null;
        try {
            firstNum = otherParamService.getValueByName("本地身份证");
            firstNum+="%";
        } catch (Exception e) {
            throw new Exception("请定义本地身份证前四位");
        }
        return guestIntegrationMapper.getLocalGuestSum(firstNum);
    }

    /**
     * 获得外地客人总数
     */
    public String getOtherGuestSum() throws Exception {
        String firstNum= null;
        try {
            firstNum = otherParamService.getValueByName("本地身份证");
            firstNum+="%";
        } catch (Exception e) {
            throw new Exception("请定义本地身份证前四位");
        }
        return guestIntegrationMapper.getOtherGuestSum(firstNum);
    }

    /**
     * 获得总人数
     */
    public String getTotalSum(){
        return guestIntegrationMapper.getTotalSum();
    }
}
