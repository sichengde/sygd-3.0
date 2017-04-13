package com.sygdsoft.mapper;

import com.sygdsoft.model.CountryGuestRow;
import com.sygdsoft.model.GuestIntegration;
import com.sygdsoft.sqlProvider.CheckInIntegrationSql;
import com.sygdsoft.sqlProvider.GuestIntegrationSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-02-09.
 */
public interface GuestIntegrationMapper extends MyMapper<GuestIntegration>{
    /**
     * 获取来店人数
     * @param beginTime 起始来店时间
     * @param endTime  终止来店时间
     * @param guestSource 客源
     * @param cardIdFirstFour 身份证前四位
     * @param like 像还是不像
     * @param foreigner 外宾
     * @return
     */
    @SelectProvider(type = GuestIntegrationSql.class,method = "getSumNum")
    @ResultType(Integer.class)
    Integer getSumNum(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("guestSource") String guestSource,@Param("cardIdFirstFour")String cardIdFirstFour,@Param("like")Boolean like,@Param("foreigner")Boolean foreigner);

    /**
     * 根据时间获得列表
     */
    @SelectProvider(type =GuestIntegrationSql.class,method ="getList" )
    @Results(value = {
            @Result(column = "card_id",property = "cardId"),
            @Result(column = "self_account",property = "selfAccount"),
            @Result(column = "reach_time",property = "reachTime"),
            @Result(column = "if_in",property = "ifIn"),
    })
    List<CountryGuestRow> getList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
