package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomShopDetail;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-21.
 */
public interface RoomShopDetailMapper extends MyMapper<RoomShopDetail> {
    /**
     * 链接结账时间表查询时间段内的房吧明细，因为没有结账时间，所以要链接结账明细表
     */
    @Select("SELECT * from room_shop_detail rsd LEFT JOIN debt_pay dp ON rsd.self_account=dp.self_account WHERE rsd.pay_serial is NULL and dp.done_time>#{beginTime} and dp.done_time<#{endTime}")
    @Results(value = {
            @Result(property = "totalMoney", column = "total_money"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "doneTime", column = "done_time")
    })
    List<RoomShopDetail> getRoomShopByDoneTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*聚合，不带操作员*/
    @Select("SELECT item,sum(num) num,sum(total_money) totalMoney,unit FROM (SELECT item ,num,total_money,unit from room_shop_detail rsd LEFT JOIN debt_pay dp ON rsd.self_account=dp.self_account WHERE rsd.pay_serial is NULL and dp.done_time>#{beginTime} and dp.done_time<#{endTime}) a GROUP BY item")
    List<RoomShopDetail> getSumRoomShopByDoneTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*聚合，带操作员*/
    @Select("SELECT item,sum(num) num,sum(total_money) totalMoney,unit FROM (SELECT item ,num,total_money,unit from room_shop_detail rsd LEFT JOIN debt_pay dp ON rsd.self_account=dp.self_account WHERE rsd.pay_serial is NULL and dp.done_time>#{beginTime} and dp.done_time<#{endTime} and dp.user_id=#{userId}) a GROUP BY item")
    List<RoomShopDetail> getSumRoomShopByDoneTimeUser(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 链接结账时间表查询时间段内的零售明细
     */
    @Select("SELECT * from room_shop_detail WHERE do_time>#{beginTime} and do_time<#{endTime} and pay_serial is not null")
    @Results(value = {
            @Result(property = "totalMoney", column = "total_money"),
            @Result(property = "doTime", column = "do_time"),
            @Result(property = "doneTime", column = "do_time")
    })
    List<RoomShopDetail> getRetailByDoneTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*聚合，不带操作员*/
    @Select("SELECT item,sum(num) num,sum(total_money) totalMoney,unit from room_shop_detail WHERE do_time>#{beginTime} and do_time<#{endTime} and pay_serial is not null GROUP BY item")
    List<RoomShopDetail> getSumRetailByDoneTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*聚合，带操作员*/
    @Select("SELECT item,sum(num) num,sum(total_money) totalMoney,unit from room_shop_detail WHERE do_time>#{beginTime} and do_time<#{endTime} and pay_serial is not null and user_id=#{userId} GROUP BY item")
    List<RoomShopDetail> getSumRetailByDoneTimeUser(@Param("userId") String userId,@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 查询所有没有统计出库的商品
     */
    @Select("select item,sum(num) num,sum(total_money) totalMoney from room_shop_detail where ifnull(cargo,false) = true and ifnull(storage_done,false) = false group by item")
    List<RoomShopDetail> getByStorageDone();

    /**
     * 自动出库后把所有品种设置为已出库
     */
    @Update("update room_shop_detail set storage_done=true")
    void setStorageDoneTrue();
}
