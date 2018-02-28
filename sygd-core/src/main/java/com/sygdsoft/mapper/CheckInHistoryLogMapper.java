package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-27.
 */
public interface CheckInHistoryLogMapper extends MyMapper<CheckInHistoryLog> {
    /**
     * 通过单位名称获得各个协议的消费总额
     */
    @Select("SELECT real_protocol realProtocol,sum(consume) consume FROM check_in_history_log WHERE leave_time>#{beginTime} AND leave_time<#{endTime} AND company=#{company}  GROUP BY real_protocol")
    List<CheckInHistoryLog> getConsumeGroupByProtocol(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("company") String company);
}