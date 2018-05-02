package com.sygdsoft.mapper;

import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface HuaYuanMapper extends MyMapper<DebtIntegration>{
    @Select("SELECT round(ifnull(sum(consume),0), 2) FROM debt_integration di LEFT JOIN guest_source gs ON di.guest_source = gs.guest_source WHERE not_part_in IS NOT TRUE AND ifnull(count_category,'未定义') = #{countCategory} AND do_time>#{beginTime} AND do_time<#{endTime}")
    @ResultType(Double.class)
    Double getGuestSourceConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("countCategory") String countCategory);
}
