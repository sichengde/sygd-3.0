package com.sygdsoft.mapper;

import com.sygdsoft.model.Freeman;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 舒展 on 2016-07-26.
 */
public interface FreemanMapper extends MyMapper<Freeman>{
    /**
     * 为宴请人增加消费
     */
    @Update("UPDATE freeman set consume=ifnull(consume,0)+#{consume} where freeman=#{freeman}")
    void addConsume(@Param("freeman") String freeman, @Param("consume") Double consume);
}
