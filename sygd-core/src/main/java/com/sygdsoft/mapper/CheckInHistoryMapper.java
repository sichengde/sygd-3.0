package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInHistory;
import com.sygdsoft.model.CheckInHistoryLog;
import com.sygdsoft.model.User;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CheckInHistoryMapper extends MyMapper<CheckInHistory> {
    /**
     * 通过自付账号获取当时在店的所有客人
     */
    @Select("SELECT * FROM guest_map_check_in gmci,check_in_history cih WHERE gmci.card_id=cih.card_id and self_account=#{selfAccount}")
    @Results(value = {
            @Result(property = "cardId",column = "card_id"),
            @Result(property = "cardType",column = "card_type"),
            @Result(property = "birthdayTime",column = "birthday_time"),
            @Result(property = "lastTime",column = "last_time"),
            @Result(property = "roomId",column = "room_id")
    })
    List<CheckInHistory> getListBySelfAccount(@Param("selfAccount") String selfAccount);

    @Update("UPDATE check_in_history SET num=num-1 WHERE card_id=#{cardId}")
    void minusOneNum(@Param("cardId") String cardId);
}
