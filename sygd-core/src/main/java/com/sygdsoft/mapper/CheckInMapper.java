package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckIn;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by 舒展 on 2016-03-21.
 */
public interface CheckInMapper extends MyMapper<CheckIn>{
    @Update("UPDATE check_in SET consume=(SELECT round(sum(consume),2) FROM debt WHERE debt.room_id=#{roomId}),deposit=(SELECT sum(deposit) FROM debt WHERE debt.room_id=#{roomId}) WHERE room_id=#{roomId}")
    void updateGuestInMoney(@Param("roomId")String roomId);

    @Select("SELECT room_id roomId ,final_room_price finalRoomPrice,consume FROM check_in WHERE ifnull(consume, 0) < check_in.final_room_price;")
    List<CheckIn> getNotAddRoomPrice();
}
