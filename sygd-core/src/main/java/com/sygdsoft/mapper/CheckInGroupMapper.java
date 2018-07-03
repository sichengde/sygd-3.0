package com.sygdsoft.mapper;

import com.sygdsoft.model.CheckInGroup;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 舒展 on 2016-05-02.
 */
public interface CheckInGroupMapper extends MyMapper<CheckInGroup> {
    @Update("UPDATE check_in_group SET consume = (SELECT round(sum(consume), 2) FROM debt WHERE debt.group_account =#{groupAccount}),deposit=(SELECT round(sum(deposit),2) FROM debt WHERE debt.group_account=#{groupAccount}) WHERE group_account=#{groupAccount}")
    void updateGroupMoney(@Param("groupAccount")String groupAccount);
}
