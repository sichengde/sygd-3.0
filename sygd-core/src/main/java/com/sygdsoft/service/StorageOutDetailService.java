package com.sygdsoft.service;

import com.sygdsoft.mapper.StorageOutDetailMapper;
import com.sygdsoft.model.StorageOutDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 */
@Service
@SzMapper(id = "storageOutDetail")
public class StorageOutDetailService extends BaseService<StorageOutDetail> {
    @Autowired
    StorageOutDetailMapper storageOutDetailMapper;

    public List<StorageOutDetail> getStorageOutDetailParse(Date beginTime, Date endTime, String house) {
        if (house == null) {
            return this.storageOutDetailMapper.getStorageOutDetailParse(beginTime, endTime);
        } else {
            return this.storageOutDetailMapper.getStorageOutDetailParseHouse(beginTime, endTime, house);
        }
    }

}