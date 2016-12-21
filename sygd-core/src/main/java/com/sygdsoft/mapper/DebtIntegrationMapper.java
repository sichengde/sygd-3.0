package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.jsonModel.RoomCategoryLine;
import com.sygdsoft.util.MyMapper;
import com.sygdsoft.model.DebtIntegration;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-07.
 */
public interface DebtIntegrationMapper extends MyMapper<DebtIntegration> {
    /**
     * 根据时间获得房类销售分析
     */
    @Select("SELECT count(*) count,sum(consume) consume,category,categoryRoom FROM (SELECT debt_integration.category category,consume,room.category categoryRoom from debt_integration LEFT JOIN room ON debt_integration.room_id=room.room_id WHERE debt_integration.category in ('全日房费','小时房费','加收房租') and debt_integration.do_time>#{beginTime} and debt_integration.do_time<#{endTime}) a GROUP BY a.category ,a.categoryRoom ORDER BY categoryRoom")
    List<DebtIntegration> parseRoomCategoryDebt(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得时间段内该房类走势
     */
    @Select("SELECT FORMAT(sum(consume) / count(*), 2) average,categoryRoom,date FROM(SELECT consume,room.category categoryRoom,do_time FROM debt_integration LEFT JOIN room ON debt_integration.room_id = room.room_id WHERE debt_integration.category IN ('全日房费', '小时房费', '加收房租') and room.category=#{category}) a RIGHT JOIN calendar ON date_format(calendar.date, '%Y-%m-%d') = date_format(a.do_time, '%Y-%m-%d') WHERE calendar.date>=#{beginTime} and calendar.date<=#{endTime} GROUP BY date ,categoryRoom ORDER BY date")
    List<RoomCategoryLine> parseRoomCategoryDebtLine(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("category") String category);


    /**
     * 根据时间获得发生额
     */
    /*，分，房吧，零售和房费*/
    @Select("SELECT ifnull(sum(consume),0) consume FROM debt_integration WHERE ifnull(category,'未定义') !=\'转入\' and do_time>#{beginTime} and do_time<#{endTime} and point_of_sale=#{pointOfSale}")
    @ResultType(String.class)
    String getSumByPointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /*啥也不分，分，房吧，零售和房费*/
    @Select("SELECT ifnull(sum(consume),0) consume FROM debt_integration WHERE ifnull(category,'未定义') !=\'转入\' and do_time>#{beginTime} and do_time<#{endTime}")
    @ResultType(String.class)
    String getSumConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据时间获得发生额（线性数据，包含了宴请）
     */
    /*，分，房吧，零售和房费*/
    @Select("SELECT calendar.date date,ifnull(money,0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(consume), 0) money,date_format(do_time,'%Y-%m-%d') date FROM debt_integration WHERE ifnull(category, '未定义') != '转入' AND do_time >#{beginTime} and do_time<#{endTime} and point_of_sale=#{pointOfSale} GROUP BY date) a ON calendar.date=a.date WHERE calendar.date>#{beginTime} AND calendar.date<#{endTime} ORDER BY calendar.date")
    List<HotelParseLineRow> getSumDateLineByPointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /*啥也不分，分，房吧，零售和房费*/
    @Select("SELECT calendar.date date,ifnull(money,0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(consume), 0) money,date_format(do_time,'%Y-%m-%d') date FROM debt_integration WHERE ifnull(category, '未定义') != '转入' AND do_time >#{beginTime} and do_time<#{endTime} GROUP BY date) a ON calendar.date=a.date WHERE calendar.date>#{beginTime} AND calendar.date<#{endTime} ORDER BY calendar.date")
    List<HotelParseLineRow> getSumDateLineConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}
