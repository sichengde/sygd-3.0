package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInSnapshot;
import com.sygdsoft.sqlProvider.CheckInSnapshotSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.Map;

public interface CheckInSnapshotMapper extends MyMapper<CheckInSnapshot> {
    @Delete("delete from room_state_report where report_time=#{debtDate}")
    void deleteByDate(@Param("debtDate") Date debtDate);

    @SelectProvider(type = CheckInSnapshotSql.class, method = "getSum")
    Double getSum(@Param("field") String field, @Param("date") Date date);

    @SelectProvider(type = CheckInSnapshotSql.class, method = "getCount")
    Integer getCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("guestSource") String guestSource);
}
