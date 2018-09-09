package com.sygdsoft.mapper;

import com.sygdsoft.model.ReportStore;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ReportStoreMapper extends MyMapper<ReportStore> {
    @Delete("DELETE FROM report_store WHERE name=#{name} AND identify=#{identify}")
    void clear(@Param("name") String name, @Param("identify") String identify);

    @Select("SELECT * from report_store WHERE type=#{type} AND name=#{name} AND identify=#{identify}")
    List<ReportStore> getList(@Param("type") String type, @Param("name") String name, @Param("identify") String identify);
}
