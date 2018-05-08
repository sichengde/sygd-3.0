package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.model.DeskInHistory;
import com.sygdsoft.sqlProvider.DeskInHistorySql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
public interface DeskInHistoryMapper extends MyMapper<DeskInHistory>{
    /**
     * 获取人数和平均消费线性统计
     */
    @Select("SELECT calendar.date date, ifnull(num, 0) num ,ifnull(money,0) money FROM calendar LEFT JOIN (SELECT date_format(do_time, '%Y-%m-%d') date,sum(num) num ,round(sum(final_price)/sum(num),2) money FROM desk_in_history WHERE do_time > #{beginTime} AND do_time < #{endTime} GROUP BY date) a ON calendar.date = a.date  WHERE calendar.date > #{beginTime} AND calendar.date < #{endTime}  ORDER BY calendar.date;")
    List<HotelParseLineRow> deskManDateChart(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT ifnull(sum(dih.total_price),0) FROM desk_in_history dih LEFT JOIN desk d ON dih.desk=d.name WHERE dih.done_time>#{beginTime} and dih.done_time<#{endTime} AND dih.point_of_sale=#{firstPointOfSale} and d.category=#{item}")
    Double getCategorySum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale, @Param("item") String item);

    @SelectProvider(type = DeskInHistorySql.class,method = "getTotalDiscount")
    @ResultType(Double.class)
    Double getTotalDiscount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale, @Param("category") String category);

    /**
     * 根据客源获取用餐人数
     */
    @Select("SELECT ifnull(sum(num), 0) FROM desk_in_history WHERE done_time >#{beginTime} and done_time<#{endTime} AND guest_source=#{guestSource}")
    @ResultType(Integer.class)
    Integer getGuestNumByGuestSource(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime,@Param("guestSource")String guestSource);
}
