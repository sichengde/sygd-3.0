package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.jsonModel.RoomCategoryLine;
import com.sygdsoft.jsonModel.report.RoomCategoryRow;
import com.sygdsoft.sqlProvider.DebtIntegrationSql;
import com.sygdsoft.util.MyMapper;
import com.sygdsoft.model.DebtIntegration;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-07.
 */
public interface DebtIntegrationMapper extends MyMapper<DebtIntegration> {

    /**
     * 获得时间段内该房类走势
     */
    @Select("SELECT FORMAT(sum(consume) / count(*), 2) average,categoryRoom,date FROM(SELECT consume,room.category categoryRoom,do_time FROM debt_integration LEFT JOIN room ON debt_integration.room_id = room.room_id WHERE debt_integration.category IN ('全日房费', '小时房费', '加收房租') and room.category=#{category}) a RIGHT JOIN calendar ON date_format(calendar.date, '%Y-%m-%d') = date_format(a.do_time, '%Y-%m-%d') WHERE calendar.date>=#{beginTime} and calendar.date<=#{endTime} GROUP BY date ,categoryRoom ORDER BY date")
    List<RoomCategoryLine> parseRoomCategoryDebtLine(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("category") String category);

    /**
     * 获得发生额线性走势
     */
    @Select("SELECT calendar.date date, ifnull(money, 0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(consume), 0) money, date_format(do_time, '%Y-%m-%d') date FROM debt_integration WHERE ifnull(category, '未定义') != '转入' AND do_time > #{beginTime} AND do_time < #{endTime} GROUP BY date) a ON calendar.date = a.date  WHERE calendar.date > #{beginTime} AND calendar.date < #{endTime}  ORDER BY calendar.date;")
    List<HotelParseLineRow> roomConsumeChart(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据时间获得发生额（线性数据，包含了宴请）
     */
    /*，分，房吧，零售和房费*/
    @Select("SELECT calendar.date date,ifnull(money,0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(consume), 0) money,date_format(do_time,'%Y-%m-%d') date FROM debt_integration WHERE ifnull(category, '未定义') != '转入' AND do_time >#{beginTime} and do_time<#{endTime} and point_of_sale=#{pointOfSale} GROUP BY date) a ON calendar.date=a.date WHERE calendar.date>#{beginTime} AND calendar.date<#{endTime} ORDER BY calendar.date")
    List<HotelParseLineRow> getSumDateLineByPointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /*啥也不分，分，房吧，零售和房费*/
    @Select("SELECT calendar.date date,ifnull(money,0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(consume), 0) money,date_format(do_time,'%Y-%m-%d') date FROM debt_integration WHERE ifnull(category, '未定义') != '转入' AND do_time >#{beginTime} and do_time<#{endTime} GROUP BY date) a ON calendar.date=a.date WHERE calendar.date>#{beginTime} AND calendar.date<#{endTime} ORDER BY calendar.date")
    List<HotelParseLineRow> getSumDateLineConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获得时间段内客源消费情况（只算房费）
     */
    @Select("SELECT guest_source guestSource,sum(consume) consume FROM debt_integration where do_time >#{beginTime} and do_time<#{endTime} and point_of_sale='房费' GROUP BY guest_source ")
    List<DebtIntegration> getSumRoomConsumeByDateGuestSource(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据营业部门聚合消费
     */
    @Select("SELECT sum(consume) consume,count(if(consume<0,NULL ,consume)) count,point_of_sale pointOfSale FROM debt_integration WHERE do_time>#{beginTime} AND do_time<#{endTime} GROUP BY point_of_sale")
    List<DebtIntegration> getSumConsumeByDatePointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据操作员币种聚合押金
     */
    @Select("SELECT sum(deposit) deposit,user_id userId,currency FROM debt_integration WHERE do_time>#{beginTime} AND do_time<#{endTime} and deposit is not null GROUP BY user_id,currency")
    List<DebtIntegration> getSumDepositByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据操作员，币种，时间，算出单退押金总和
     */
    @SelectProvider(type = DebtIntegrationSql.class,method = "getDepositByUserCurrencyDate")
    @ResultType(Double.class)
    Double getDepositByUserCurrencyDate(@Param("userId") String userId, @Param("currency") String currency, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据发生时间获得消费总额
     */
    @SelectProvider(type = DebtIntegrationSql.class,method = "getSumConsumeByDoTime")
    @ResultType(Double.class)
    Double getSumConsumeByDoTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale, @Param("excludeChange") boolean excludeChange);

    /**
     * 通过营业部门进行聚合
     * @param beginTime
     * @param endTime
     * @param userId
     * @return
     */
    @SelectProvider(type = DebtIntegrationSql.class,method = "getList")
    List<DebtIntegration> getList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("userId") String userId);

    /**
     * 获得某个时间点前的在店预付
     */
    @SelectProvider(type = DebtIntegrationSql.class,method = "getSumDepositByEndTime")
    @ResultType(Double.class)
    Double getSumDepositByEndTime(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime,@Param("userId")String userId);
}
