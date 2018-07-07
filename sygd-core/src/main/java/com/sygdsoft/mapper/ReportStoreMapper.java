package com.sygdsoft.mapper;

import com.sygdsoft.model.ReportStore;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface ReportStoreMapper extends MyMapper<ReportStore> {
    @Delete("DELETE FROM report_store WHERE name=#{name} AND identify=#{identify}")
    void clear(@Param("name") String name,@Param("identify") String identify);
}
