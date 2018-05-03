package com.sygdsoft.service;

import com.sygdsoft.mapper.GuestSnapshotMapper;
import com.sygdsoft.model.GuestSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@SzMapper(id = "guestSnapshot")
public class GuestSnapshotService extends BaseService<GuestSnapshot>{
    @Autowired
    GuestSnapshotMapper guestSnapshotMapper;
    /*获取某一时间段的数据*/
    public GuestSnapshot getSum(Date beginTime, Date endTime){
        return guestSnapshotMapper.getSum(beginTime,endTime);
    }

    public void deleteByDate(Date debtDate) {
        guestSnapshotMapper.deleteByDate(debtDate);
    }
}
