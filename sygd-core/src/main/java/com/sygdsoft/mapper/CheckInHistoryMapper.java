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
     * 通过离店结算序列号获取当时在店的所有客人
     */
    @Select("SELECT * FROM check_in_history RIGHT JOIN (SELECT * FROM check_in_history_log WHERE check_out_serial=#{checkOutSerial}) a ON a.card_id=check_in_history.card_id")
    @Results(value = {
            @Result(property = "cardId",column = "card_id"),
            @Result(property = "cardType",column = "card_type"),
            @Result(property = "birthdayTime",column = "birthday_time"),
            @Result(property = "lastTime",column = "last_time"),
            @Result(property = "roomId",column = "room_id")
    })
    List<CheckInHistory> getListByCheckOutSerial(@Param("checkOutSerial") String checkOutSerial);

    @Update("UPDATE check_in_history SET num=num-1 WHERE card_id=#{cardId}")
    void minusOneNum(@Param("cardId") String cardId);
}
