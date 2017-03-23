package com.sygdsoft.mapper;

import com.sygdsoft.model.StorageInDetail;
import com.sygdsoft.service.SzMapper;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 舒展 on 2016-11-17.
 */
public interface StorageInDetailMapper extends MyMapper<StorageInDetail> {
    /**
     * 获得当前仓库中各个货品数量总和
     */
    @Select("select house,cargo,sum(remain) remain from storage_in_detail WHERE house = #{house} GROUP BY house,cargo")
    List<StorageInDetail> getSumNumByHouse(@Param("house") String house);

    /**
     * 获得所有仓库中各个货品数量总和
     */
    @Select("select house,cargo,unit,sum(remain) remain,FORMAT(sum(remain*price)/sum(remain),2) price from storage_in_detail GROUP BY house,cargo,unit")
    List<StorageInDetail> getSumNum();

    /**
     * 获得当前仓库某个商品的数量总和
     */
    @Select("select house,cargo,sum(remain) remain from storage_in_detail WHERE house = #{house} and cargo=#{cargo} GROUP BY house,cargo")
    List<StorageInDetail> getSumNumByNameHouse(@Param("house") String house, @Param("cargo") String cargo);

    /**
     * 获得该商品的入库记录（大于0的）
     */
    @Select("select * from storage_in_detail where cargo=#{cargo} and ifnull(remain,0)>0 and house=#{house}")
    @Results(value = {
            @Result(column = "begin_time", property = "beginTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "storage_in_serial", property = "storageInSerial"),
    })
    List<StorageInDetail> getByCargoExist(@Param("house") String house, @Param("cargo") String cargo);

    /**
     * 根据仓库名称删除
     */
    @Delete("delete from storage_in_detail where house=#{house}")
    void deleteByHouse(@Param("house") String house);
}
