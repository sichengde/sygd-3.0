package com.sygdsoft.mapper;

import com.alibaba.fastjson.JSONObject;
import com.sygdsoft.jsonModel.SaleManRich;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface SaleManMapper extends MyMapper<SaleManRich> {
    @Select("SELECT round(ifnull(sum(dp.debt_money), 0), 2) money, currency, company, sale_man saleMan FROM company c LEFT JOIN debt_pay dp ON dp.company = c.name WHERE sale_man IS NOT NULL AND dp.done_time > #{beginTime} AND dp.done_time < #{endTime} GROUP BY sale_man, currency UNION ALL SELECT round(ifnull(sum(dp.pay_money), 0), 2) money, currency, company, sale_man saleMan FROM company c LEFT JOIN desk_pay dp ON dp.company = c.name WHERE sale_man IS NOT NULL AND dp.done_time > #{beginTime} AND dp.done_time < #{endTime} GROUP BY sale_man, currency,company")
    List<SaleManRich> getSaleManGroup(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
