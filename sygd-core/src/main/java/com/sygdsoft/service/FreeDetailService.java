package com.sygdsoft.service;

import com.sygdsoft.model.FreeDetail;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-07-26.
 */
@Service
@SzMapper(id = "freeDetail")
public class FreeDetailService extends BaseService<FreeDetail>{
    /**
     * 根据结账序列号删除
     */
    public void deleteByPaySerial(String paySerial) throws Exception {
        FreeDetail freeDetail=new FreeDetail();
        freeDetail.setPaySerial(paySerial);
        this.delete(freeDetail);
    }
}
