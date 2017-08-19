package com.sygdsoft.mapper;

import com.sygdsoft.model.CashBox;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface CashBoxMapper extends MyMapper<CashBox>{
    @Select("SELECT id, begin_time beginTime, end_time endTime, user_id userId, before_money beforeMoney, get_money getMoney, remain,currency_detail currencyDetail FROM cash_box ORDER BY id DESC LIMIT 1;")
    CashBox cashBoxGetLast();
}
