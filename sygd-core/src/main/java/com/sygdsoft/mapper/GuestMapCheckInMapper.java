package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.model.GuestMapCheckIn;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒展 on 2017-02-09.
 */
public interface GuestMapCheckInMapper extends MyMapper<GuestMapCheckIn> {
    /**
     * 通过身份证号和房类获取上次开房房价
     */
    @Select("SELECT final_room_price finalRoomPrice,room_id roomId,reach_time reachTime FROM guest_map_check_in LEFT JOIN check_in_history_log ON guest_map_check_in.self_account = check_in_history_log.self_account WHERE check_in_history_log.room_category =#{roomCategory} AND guest_map_check_in.card_id=#{cardId}  ORDER BY check_in_history_log.id DESC")
    List<CheckInHistoryLog> getHistoryRoomPriceByCardId(@Param("cardId") String cardId , @Param("roomCategory") String roomCategory);

    @Delete("delete from guest_map_check_in where card_id=#{cardId}")
    void deleteByCardId(@Param("cardId") String cardId);
}
