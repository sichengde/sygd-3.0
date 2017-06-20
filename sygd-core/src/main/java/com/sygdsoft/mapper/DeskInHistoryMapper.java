package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.model.DeskInHistory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
public interface DeskInHistoryMapper extends MyMapper<DeskInHistory>{
    /**
     * 获取人数和平均消费线性统计
     */
    @Select("SELECT calendar.date date, ifnull(num, 0) num ,ifnull(money,0) money FROM calendar LEFT JOIN (SELECT date_format(do_time, '%Y-%m-%d') date,sum(num) num ,truncate(sum(final_price)/sum(num),2) money FROM desk_in_history WHERE do_time > #{beginTime} AND do_time < #{endTime} GROUP BY date) a ON calendar.date = a.date  WHERE calendar.date > #{beginTime} AND calendar.date < #{endTime}  ORDER BY calendar.date;")
    List<HotelParseLineRow> deskManDateChart(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
