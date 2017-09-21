package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.jsonModel.report.DeskCategoryOut;
import com.sygdsoft.jsonModel.report.DeskProfitOut;
import com.sygdsoft.model.DeskDetailHistory;
import com.sygdsoft.sqlProvider.DeskDetailHistorySql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
public interface DeskDetailHistoryMapper extends MyMapper<DeskDetailHistory> {
    /**
     * 获得该时间段的结算款（不带操作员）
     */
    /*分二级营业部门*/
    @Select("SELECT ifnull(sum(ddh.price*ddh.num),0) FROM desk_detail_history ddh LEFT JOIN sale_count sc ON ddh.category=sc.name where ddh.done_time>#{beginTime} and ddh.done_time<#{endTime} and sc.second_point_of_sale=#{secondPointOfSale} and sc.first_point_of_sale=#{firstPointOfSale} and ifnull(disabled,false)=false")
    Double getDeskMoneyByDatePointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale, @Param("secondPointOfSale") String secondPointOfSale);

    /*只统计一级营业部门*/
    @Select("SELECT ifnull(sum(ddh.price*ddh.num),0) FROM desk_detail_history ddh LEFT JOIN sale_count sc ON ddh.category=sc.name where ddh.done_time>#{beginTime} and ddh.done_time<#{endTime} and sc.first_point_of_sale=#{firstPointOfSale} and ifnull(disabled,false)=false")
    Double getDeskMoneyByDateFirstPointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale);

    /**
     * 获得该时间段的结算款（不带操作员）（线性数据，包含了宴请）
     */
    /*分二级营业部门*/
    @Select("SELECT calendar.date    date, ifnull(money, 0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(ddh.price * ddh.num), 0) money, date_format(done_time, '%Y-%m-%d')  date FROM desk_detail_history ddh LEFT JOIN sale_count sc ON ddh.category = sc.name WHERE ddh.done_time > #{beginTime} AND ddh.done_time < #{endTime} AND sc.first_point_of_sale = #{firstPointOfSale} AND sc.second_point_of_sale = #{secondPointOfSale} AND ifnull(disabled, FALSE) = FALSE GROUP BY date) a ON calendar.date = a.date WHERE calendar.date >= #{beginTime} AND calendar.date < #{endTime} ORDER BY calendar.date")
    List<HotelParseLineRow> getDeskMoneyDateLineByDatePointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale, @Param("secondPointOfSale") String secondPointOfSale);

    /*只统计一级营业部门*/
    @Select("SELECT calendar.date    date, ifnull(money, 0) money FROM calendar LEFT JOIN (SELECT ifnull(sum(ddh.price * ddh.num), 0) money, date_format(done_time, '%Y-%m-%d')  date FROM desk_detail_history ddh LEFT JOIN sale_count sc ON ddh.category = sc.name WHERE ddh.done_time > #{beginTime} AND ddh.done_time < #{endTime} AND sc.first_point_of_sale = #{firstPointOfSale} AND ifnull(disabled, FALSE) = FALSE GROUP BY date) a ON calendar.date = a.date WHERE calendar.date > #{beginTime} AND calendar.date < #{endTime} ORDER BY calendar.date")
    List<HotelParseLineRow> getDeskMoneyDateLineByDateFirstPointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale);

    /**
     * 获得该时间段该币种的对象列表（不带操作员）
     */
    @Select("SELECT * FROM desk_detail_history ddh LEFT JOIN sale_count sc ON ddh.category=sc.name LEFT JOIN point_of_sale pos ON sc.second_point_of_sale=pos.second_point_of_sale where ddh.done_time>#{beginTime} and ddh.done_time<#{endTime} and sc.second_point_of_sale=#{secondPointOfSale} and pos.first_point_of_sale=#{firstPointOfSale} and ifnull(disabled,false)=false")
    @Results(value = {
            @Result(property = "ckSerial", column = "ck_serial"),
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "foodSign", column = "food_sign"),
    })
    List<DeskDetailHistory> getByDatePointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale, @Param("secondPointOfSale") String secondPointOfSale);

    /**
     * 获得该时间段该菜品类别的人数合计
     */
    @Select("SELECT ifnull(sum(ddh.num),0) FROM desk_detail_history ddh LEFT JOIN sale_count sc ON ddh.category=sc.name  where ddh.done_time>#{beginTime} and ddh.done_time<#{endTime} and sc.name=#{saleCount} and sc.first_point_of_sale=#{firstPointOfSale} and ifnull(disabled,false)=false")
    Integer getNumByDateSaleCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("firstPointOfSale") String firstPointOfSale, @Param("saleCount") String saleCount);

    /**
     * 类别分析
     */
    /*不限定账单号，分析三级类别*/
    @Select("SELECT category , sum(total) total FROM desk_detail_history ddh WHERE ddh.point_of_sale =#{pointOfSale} and done_time>#{beginTime} AND done_time<#{endTime} and ifnull(disabled,false)=false group by category")
    List<DeskCategoryOut> getCategoryParse(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /*根据某个账单，分析三级类别*/
    @Select("SELECT category , sum(total) total FROM desk_detail_history ddh WHERE ck_serial=#{ckSerial} and ifnull(disabled,false)=false group by category")
    List<DeskCategoryOut> getCategoryParseBySerial(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale, @Param("ckSerial") String ckSerial);

    /*不限定账单号，分析二级类别*/
    @Select("SELECT second_point_of_sale as category , sum(total) total FROM desk_detail_history ddh LEFT JOIN sale_count sc ON sc.name=ddh.category WHERE ddh.point_of_sale =#{pointOfSale} and done_time>#{beginTime} AND done_time<#{endTime} and ifnull(disabled,false)=false group by second_point_of_sale")
    List<DeskCategoryOut> getCategorySecondParse(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /*根据某个账单，分析二级类别*/
    @Select("SELECT second_point_of_sale as category, sum(total) total FROM desk_detail_history ddh LEFT JOIN sale_count sc ON sc.name=ddh.category WHERE ddh.point_of_sale =#{pointOfSale} and done_time>#{beginTime} AND done_time<#{endTime} and ck_serial=#{ckSerial} and ifnull(disabled,false)=false group by second_point_of_sale")
    List<DeskCategoryOut> getCategorySecondParseBySerial(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale, @Param("ckSerial") String ckSerial);

    /**
     * 毛利率，连menu查询
     */
    /*不带类别*/
    @Select("SELECT food_name foodName , sum(num) num,sum(after_discount) afterDiscount,(sum(num)*ifnull(cost,0)) totalCost FROM desk_detail_history ddh LEFT JOIN menu m ON ddh.food_sign=m.name WHERE ddh.point_of_sale=#{pointOfSale} and done_time>#{beginTime} AND done_time<#{endTime} and ddh.price is not null and ifnull(cost,0)>0 and ifnull(disabled,false)=false group by food_name")
    @Results(value = {
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "foodSign", column = "food_sign"),
    })
    List<DeskProfitOut> getDeskProfitList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    /*带类别*/
    @Select("SELECT food_name foodName , sum(num) num,sum(after_discount) afterDiscount,(sum(num)*ifnull(cost,0)) totalCost FROM desk_detail_history ddh LEFT JOIN menu m ON ddh.food_sign=m.name WHERE ddh.point_of_sale=#{pointOfSale} and done_time>#{beginTime} AND done_time<#{endTime} and ddh.price is not null and ifnull(cost,0)>0 and m.category=#{category} and ifnull(disabled,false)=false group by food_name")
    @Results(value = {
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "foodSign", column = "food_sign"),
    })
    List<DeskProfitOut> getDeskProfitListByCategory(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale, @Param("category") String category);

    /**
     * 查询所有没有统计出库的商品
     */
    @Select("select food_sign foodSign,sum(num) num,sum(after_discount) afterDiscount from desk_detail_history where ifnull(cargo,false) = true and ifnull(storage_done,false) = false and ifnull(disabled,false)=false and point_of_sale=#{pointOfSale} group by food_sign")
    List<DeskDetailHistory> getByStorageDone(@Param("pointOfSale")String pointOfSale);

    /**
     * 自动出库后把所有品种设置为已出库
     */
    @Update("update desk_detail_history set storage_done=true")
    void setStorageDoneTrue();

    /**
     * 设置为不可用
     */
    @Update("update desk_detail_history set disabled =true where ck_serial=#{serial}")
    void setDisabledBySerial(@Param("serial") String serial);

    /**
     * 根据查找列表
     */
    @SelectProvider(type = DeskDetailHistorySql.class,method = "getList")
    @Results(value = {
            @Result(property = "ckSerial", column = "ck_serial"),
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "foodSign", column = "food_sign"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "ifDiscount", column = "if_discount"),
            @Result(property = "afterDiscount", column = "after_discount"),
            @Result(property = "foodSet", column = "food_set"),
            @Result(property = "cookRoom", column = "cook_room"),
            @Result(property = "storageDone", column = "storage_done"),
    })
    List<DeskDetailHistory> getList(@Param("ckSerial") String ckSerial,  @Param("orderByList") String orderByList);


    @SelectProvider(type = DeskDetailHistorySql.class,method = "getSumList")
    @Results(value = {
            @Result(property = "ckSerial", column = "ck_serial"),
            @Result(property = "foodName", column = "food_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "pointOfSale", column = "point_of_sale"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "foodSign", column = "food_sign"),
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "ifDiscount", column = "if_discount"),
            @Result(property = "afterDiscount", column = "after_discount"),
            @Result(property = "foodSet", column = "food_set"),
            @Result(property = "cookRoom", column = "cook_room"),
            @Result(property = "storageDone", column = "storage_done"),
    })
    List<DeskDetailHistory> getSumList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale, @Param("category") String category, @Param("mergeFood") Boolean mergeFood);
}
