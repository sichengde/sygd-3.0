package com.sygdsoft.service;

import com.sygdsoft.jsonModel.report.GuestSourceRoomCategoryRow;
import com.sygdsoft.mapper.CheckInIntegrationMapper;
import com.sygdsoft.model.CheckInIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-01-05.
 */
@Service
@SzMapper(id = "checkInIntegration")
public class CheckInIntegrationService extends BaseService<CheckInIntegration>{
    @Autowired
    CheckInIntegrationMapper checkInIntegrationMapper;
    /**
     * 获得时间段内客源与房类的销售关系
     */
    public List<GuestSourceRoomCategoryRow> guestSourceRoomCategoryParse(Date beginTime, Date endTime){
        return checkInIntegrationMapper.guestSourceRoomCategoryParse(beginTime,endTime);
    }
}
