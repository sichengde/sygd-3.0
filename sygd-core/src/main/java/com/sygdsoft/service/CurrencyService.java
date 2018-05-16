package com.sygdsoft.service;

import com.sygdsoft.mapper.CurrencyMapper;
import com.sygdsoft.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 舒展 on 2016-05-19.
 * 定义默认币种
 * 币种分为两类，实收款类和转账款类
 */
@Service
@SzMapper(id = "currency")
public class CurrencyService extends BaseService<Currency> {
    public String HY = "会员";//会员
    public String FK = "转房客";//转房客
    public String DW = "转单位";//转单位
    public String YF = "转哑房";//转哑房
    public String YQ = "宴请";//转哑房
    @Autowired
    CurrencyMapper currencyMapper;

    public Currency getByName(String name) {
        Currency currencyQuery=new Currency();
        currencyQuery.setCurrency(name);
        return currencyMapper.selectOne(currencyQuery);
    }
}
