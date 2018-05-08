package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskDetailCancelAllMapper;
import com.sygdsoft.model.DeskDetailCancelAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@SzMapper(id = "deskDetailCancelAll")
public class DeskDetailCancelAllService extends BaseService<DeskDetailCancelAll> {
    @Autowired
    DeskDetailCancelAllMapper deskDetailCancelAllMapper;
    /**
     * 通过营业部门和结账时间获取
     */
    public List<DeskDetailCancelAll> getList(String pointOfSale,Date doneTime){
        return deskDetailCancelAllMapper.getList(pointOfSale,doneTime);
    }
}
