package com.sygdsoft.mapper;

import com.sygdsoft.model.UserLog;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface UserLogMapper extends MyMapper<UserLog> {
    @Select("SELECT do_time FROM user_log ORDER BY do_time DESC LIMIT 1;")
    @ResultType(Date.class)
    Date getRecentDate();
}
