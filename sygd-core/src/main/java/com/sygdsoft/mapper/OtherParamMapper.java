package com.sygdsoft.mapper;

import com.sygdsoft.model.OtherParam;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface OtherParamMapper extends MyMapper<OtherParam> {
    @Select("select value from other_param where other_param = #{name}")
    @ResultType(String.class)
    String getValueByName(@Param("name") String name);

    @Update("update other_param set value = #{value} where other_param=#{otherParam}")
    void updateValueByName(@Param("otherParam")String otherParam,@Param("value") String value);
}
