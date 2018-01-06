package com.sygdsoft.mapper;

import com.sygdsoft.model.CompanyMoney;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompanyMoneyMapper extends MyMapper<CompanyMoney> {
    /**
     * 获得单位的可用余额列表
     */
    @Select("SELECT sum(money) money,currency FROM company_money WHERE company=#{company} GROUP BY currency")
    List<CompanyMoney> getSumList(@Param("company") String company);
}
