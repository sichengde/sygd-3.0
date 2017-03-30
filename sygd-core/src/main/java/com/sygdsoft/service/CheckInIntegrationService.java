package com.sygdsoft.service;

import com.sygdsoft.jsonModel.report.GuestSourceRoomCategoryRow;
import com.sygdsoft.mapper.CheckInIntegrationMapper;
import com.sygdsoft.model.CheckIn;
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
    /**
     * 获得一段时间内的总人数
     */
    public Integer getSumNumByDate(Date beginTime,Date endTime){
        return checkInIntegrationMapper.getSumNumByDate(beginTime,endTime);
    }
    /**
     * 获得一段时间内的外宾人数
     */
    public Integer getSumForeignerNumByDate(Date beginTime,Date endTime){
        return checkInIntegrationMapper.getSumForeignerNumByDate(beginTime,endTime);
    }

    /**
     * 获得房间对象
     */
    public CheckInIntegration getByRoomId(String roomId) {
        CheckInIntegration checkInIntegrationQuery = new CheckInIntegration();
        checkInIntegrationQuery.setRoomId(roomId);
        return checkInIntegrationMapper.selectOne(checkInIntegrationQuery);
    }
}
