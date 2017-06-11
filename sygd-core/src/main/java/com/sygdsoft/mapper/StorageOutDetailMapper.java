package com.sygdsoft.mapper;

import com.sygdsoft.model.StorageOutDetail;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 */
public interface StorageOutDetailMapper extends MyMapper<StorageOutDetail>{
    /**
     * 根据时间获取明细聚合
     */
    @Select("SELECT cargo, sum(num) num, sum(total) total, sum(old_total) oldTotal FROM storage_out_detail sod LEFT JOIN storage_out so ON sod.storage_out_serial = so.storage_out_serial WHERE so.out_time > #{beginTime} AND so.out_time < #{endTime} group by cargo")
    List<StorageOutDetail> getStorageOutDetailParse(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 根据时间和仓库获取明细聚合
     */
    @Select("SELECT cargo, sum(num) num, sum(total) total, sum(old_total) oldTotal FROM storage_out_detail sod LEFT JOIN storage_out so ON sod.storage_out_serial = so.storage_out_serial WHERE so.out_time > #{beginTime} AND so.out_time < #{endTime} and house=#{house} group by cargo,house")
    List<StorageOutDetail> getStorageOutDetailParseHouse(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("house") String house);
}
