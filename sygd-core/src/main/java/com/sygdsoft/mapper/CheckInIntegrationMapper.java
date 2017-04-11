package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.report.GuestSourceRoomCategoryRow;
import com.sygdsoft.model.CheckInIntegration;
import com.sygdsoft.model.InCategory;
import com.sygdsoft.sqlProvider.CheckInIntegrationSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

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

    @Select("SELECT count(*) FROM check_in_integration cii LEFT JOIN guest_integration gi on cii.self_account=gi.self_account WHERE cii.reach_time>#{beginTime} AND cii.reach_time<#{endTime} AND ifnull(gi.country,'中国')!='中国'")
    @ResultType(Integer.class)
    Integer getSumForeignerNumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Select("SELECT count(*) FROM check_in_integration cii LEFT JOIN guest_integration gi on cii.self_account=gi.self_account WHERE cii.reach_time>#{beginTime} AND cii.reach_time<#{endTime}")
    @SelectProvider(type = CheckInIntegrationSql.class,method = "getSumNum")
    @ResultType(Integer.class)
    Integer getSumNumByDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("guestSource") String guestSource);
}
