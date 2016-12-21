package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInGuest;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CheckInGuestMapper extends MyMapper<CheckInGuest> {
    /**
     * 通过床位号数组和房间号获得在店宾客数组
     */
    @Select("select * from check_in_guest where room_id = #{roomId} and bed in (${bedList})")
    List<CheckInGuest> getListByBedList(@Param("roomId") String roomId,@Param("bedList") String bedList);

    /**
     * 通过房号列表获得在店宾客数组
     */
    @Select("select * from check_in_guest where room_id in (${roomIdList})")
    List<CheckInGuest> getListByRoomIdList(@Param("roomIdList") String roomIdList);
}
