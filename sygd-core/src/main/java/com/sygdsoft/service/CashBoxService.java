package com.sygdsoft.service;

import com.sygdsoft.mapper.CashBoxMapper;
import com.sygdsoft.model.CashBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SzMapper(id = "cashBox")
public class CashBoxService extends BaseService<CashBox>{
    @Autowired
    CashBoxMapper cashBoxMapper;
    /**
     * 获取最近一条
     */
    public CashBox cashBoxGetLast(){
        return cashBoxMapper.cashBoxGetLast();
    }
}
