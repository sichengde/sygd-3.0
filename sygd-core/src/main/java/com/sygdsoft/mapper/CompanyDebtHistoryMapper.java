package com.sygdsoft.mapper;

import com.sygdsoft.model.CompanyDebtHistory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2017-02-23.
 */
public interface CompanyDebtHistoryMapper extends MyMapper<CompanyDebtHistory>{
    @Select("SELECT sum(debt) debt FROM company_debt_history WHERE company=#{companyName} AND done_time>#{beginTime} AND do_time<#{endTime}")
    @ResultType(Double.class)
    Double getBackMoney(@Param("companyName") String companyName, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
