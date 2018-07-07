package com.sygdsoft.service;

import com.sygdsoft.mapper.CheckInSnapshotMapper;
import com.sygdsoft.model.CheckInSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@SzMapper(id = "checkInSnapshot")
public class CheckInSnapshotService extends BaseService<CheckInSnapshot> {
    @Autowired
    CheckInSnapshotMapper checkInSnapshotMapper;

    public void deleteByDate(Date debtDate) {
        checkInSnapshotMapper.deleteByDate(debtDate);
    }

    public Double getSum(String field,Date date){
        return checkInSnapshotMapper.getSum(field,date);
    }
}
