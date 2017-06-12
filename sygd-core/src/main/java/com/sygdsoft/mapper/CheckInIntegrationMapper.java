package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInIntegration;
import com.sygdsoft.sqlProvider.CheckInIntegrationSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;

/**
 * Created by 舒展 on 2017-01-05.
 */
public interface CheckInIntegrationMapper extends MyMapper<CheckInIntegration> {
    @SelectProvider(type = CheckInIntegrationSql.class, method = "getSumCount")
    @ResultType(Double.class)
    Integer getSumCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("guestSource") String guestSource, @Param("roomCategory") String roomCategory);

    @SelectProvider(type = CheckInIntegrationSql.class, method = "getSumConsume")
    @ResultType(Double.class)
    Double getSumConsume(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("guestSource") String guestSource, @Param("roomCategory") String roomCategory);

    @SelectProvider(type = CheckInIntegrationSql.class, method = "getTotalLiveDay")
    @ResultType(Integer.class)
    Integer getTotalLiveDay(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("guestSource") String guestSource, @Param("roomCategory") String roomCategory);

    @SelectProvider(type = CheckInIntegrationSql.class, method = "getAvaRoomPrice")
    @ResultType(Double.class)
    Double getAvaRoomPrice(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("guestSource") String guestSource, @Param("roomCategory") String roomCategory);

}
