package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.report.GuestSourceRoomCategoryRow;
import com.sygdsoft.model.CheckInIntegration;
import com.sygdsoft.model.InCategory;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-01-05.
 */
public interface CheckInIntegrationMapper extends MyMapper<CheckInIntegration> {
    /**
     * 获得时间段内的客源和房类关系
     */
    @Select("SELECT  cii.guest_source guestSource ,  cii.room_category roomCategory,  sum(di.consume) consume FROM check_in_integration cii LEFT JOIN debt_integration di ON cii.self_account = di.self_account WHERE di.category IN ('全日房费', '小时房费', '加收房租') and do_time >#{beginTime} and do_time<#{endTime} GROUP BY cii.guest_source,cii.room_category ORDER BY cii.guest_source,cii.room_category;")
    List<GuestSourceRoomCategoryRow> guestSourceRoomCategoryParse(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT count(*) FROM check_in_integration cih LEFT JOIN check_in_integration_log cihl ON cih.card_id=cihl.card_id WHERE cihl.reach_time>#{beginTime} AND cihl.reach_time<#{endTime}")
    @ResultType(Integer.class)
    Integer getSumForeignerNumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT count(*) FROM check_in_integration cih LEFT JOIN check_in_integration_log cihl ON cih.card_id=cihl.card_id WHERE cihl.reach_time>? AND cihl.reach_time<? AND ifnull(cih.country,'中国')!='中国'")
    @ResultType(Integer.class)
    Integer getSumNumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
