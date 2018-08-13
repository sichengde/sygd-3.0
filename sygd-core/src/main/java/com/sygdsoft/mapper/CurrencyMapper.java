package com.sygdsoft.mapper;

import com.sygdsoft.model.Currency;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CurrencyMapper extends MyMapper<Currency> {
    @Select("SELECT currency FROM currency WHERE pay_back_rmb=TRUE ;")
    @ResultType(String.class)
    List<String> getToRMBList();
}
