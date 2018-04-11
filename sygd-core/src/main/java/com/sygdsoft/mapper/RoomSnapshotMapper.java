package com.sygdsoft.mapper;

import com.sygdsoft.model.RoomSnapshot;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface RoomSnapshotMapper extends MyMapper<RoomSnapshot> {
    @Delete("delete from room_snapshot where report_time=#{date}")
    void deleteByDate(@Param("date") Date date);
}
