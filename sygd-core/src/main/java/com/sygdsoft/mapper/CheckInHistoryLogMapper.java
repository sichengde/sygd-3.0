package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.GuestParseRow;
import com.sygdsoft.jsonModel.report.GuestSourceRoomCategoryRow;
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
     * 获得时间段内客源消费情况
     */
    @Select("SELECT sum(consume) totalConsume,count(*) num,format(sum(consume)/count(*),2) averageConsume,guest_source guestSource FROM check_in_history_log WHERE leave_time>#{beginTime} AND leave_time<#{endTime} GROUP BY guest_source")
    List<GuestParseRow> guestSourceParse(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}