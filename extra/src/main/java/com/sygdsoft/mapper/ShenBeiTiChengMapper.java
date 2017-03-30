package com.sygdsoft.mapper;

import com.sygdsoft.model.Debt;
import com.sygdsoft.model.ExtraSql;
import com.sygdsoft.model.RoomShopDetailWithCurrency;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2017-03-30.
 */
public interface ShenBeiTiChengMapper extends MyMapper<RoomShopDetailWithCurrency> {
    /**
     * 获取房吧账务并且包含币种
     */
    @SelectProvider(type = ExtraSql.class,method = "getList")
    List<RoomShopDetailWithCurrency> getRoomShop(@Param("userId") String userId, @Param("beginTime") Date beginTime,@Param("endTime") Date endTime);

    /**
     * 获得全部的在店押金值
     */
    @Select("select ifnull(sum(deposit),0) from debt where deposit is not null and currency=\'押金\'")
    @ResultType(Double.class)
    Double getDepositMoneyAll();
}
