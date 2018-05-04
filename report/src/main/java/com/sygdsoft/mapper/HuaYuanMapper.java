package com.sygdsoft.mapper;

import com.sygdsoft.model.DebtIntegration;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface HuaYuanMapper extends MyMapper<DebtIntegration>{
    /**
     * 获取客源大类消费
     * @param beginTime
     * @param endTime
     * @param countCategory
     * @return
     */
    @Select("SELECT round(ifnull(sum(consume),0), 2) FROM debt_integration di LEFT JOIN guest_source gs ON di.guest_source = gs.guest_source WHERE not_part_in IS NOT TRUE AND ifnull(count_category,'未定义') = #{countCategory} AND do_time>#{beginTime} AND do_time<#{endTime}")
    @ResultType(Double.class)
    Double getGuestSourceConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("countCategory") String countCategory);

    /**
     * 获取已出租会议室数
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select("SELECT count(*) FROM room r LEFT JOIN check_in_integration ci ON r.room_id = ci.room_id WHERE ifnull(r.if_room, FALSE) = FALSE AND consume IS NOT NULL AND reach_time > #{beginTime} AND reach_time < #{endTime}")
    @ResultType(Double.class)
    Double getNotRoomSaleCount(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

    /**
     * 获取会议室消费
     * @param beginTime
     * @param endTime
     * @return
     */
    @Select("SELECT sum(consume) FROM room r LEFT JOIN check_in_integration ci ON r.room_id = ci.room_id WHERE ifnull(r.if_room, FALSE) = FALSE AND consume IS NOT NULL AND reach_time > #{beginTime} AND reach_time < #{endTime}")
    @ResultType(Double.class)
    Double getNotRoomSaleConsume(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

    @Select("SELECT ifnull(round(sum(total_money),2),0) FROM room_shop_detail WHERE do_time>#{beginTime} and do_time<#{endTime} and point_of_sale_shop=#{pointOfSaleShop}")
    @ResultType(Double.class)
    Double getRoomShopConsume(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime,@Param("pointOfSaleShop")String pointOfSaleShop);
}
