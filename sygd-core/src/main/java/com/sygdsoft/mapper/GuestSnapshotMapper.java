package com.sygdsoft.mapper;

import com.sygdsoft.model.GuestSnapshot;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface GuestSnapshotMapper extends MyMapper<GuestSnapshot> {
    @Select("SELECT ifnull(round(sum(come),2),0) sumCome, ifnull(round(sum(exist),2),0) sumExist FROM guest_snapshot WHERE report_time>=#{beginTime} and report_time<#{endTime}")
    GuestSnapshot getSum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    @Delete("delete from guest_snapshot where report_time=#{debtDate}")
    void deleteByDate(@Param("debtDate") Date debtDate);
}
