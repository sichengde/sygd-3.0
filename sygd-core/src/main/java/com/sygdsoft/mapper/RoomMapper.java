package com.sygdsoft.mapper;

import com.sygdsoft.model.Room;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-03-22.
 */
public interface RoomMapper extends MyMapper<Room> {
    /**
     * 更新房态
     */
    @Update("update room set state=#{state} where room_id=#{roomId}")
    void updateRoomState(@Param("roomId") String roomId, @Param("state") String state);

    /**
     * 散客结算时更新房态
     * @param roomId 房间号
     * @param state 状态
     * @param date 离店日期
     */
    @Update("update room set state=#{state} , dirty='0' , check_out_time=#{date} where room_id=#{roomId}")
    void updateRoomStateGuestOut(@Param("roomId") String roomId, @Param("state") String state, @Param("date") Date date);

    /**
     * 将房间设置为脏房
     * @param roomId
     */
    @Update("update room set dirty='1' where room_id=#{roomId}")
    void dirtyRoom(@Param("roomId") String roomId);

    /**
     * 获得该房类的全部房数
     */
    @Select("select count(*) from room where category=#{category} and if_room is true")
    @ResultType(Integer.class)
    Integer getTotalCategoryNum(@Param("category") String category);

    /**
     * 通过房号字符串获得房间对象数组
     */
    @Select("select * from room where room_id in (${roomId})")
    @Results(value = {
            @Result(property = "roomId",column = "room_id"),
            @Result(property = "totalBed",column = "total_bed"),
            @Result(property = "ifRoom",column = "if_room"),
    })
    List<Room> getListByRoomIdString(@Param("roomId") String roomId);
}
