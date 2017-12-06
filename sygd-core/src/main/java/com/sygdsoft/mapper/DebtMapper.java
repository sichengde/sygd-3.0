package com.sygdsoft.mapper;

import com.sygdsoft.model.Debt;
import com.sygdsoft.sqlProvider.DebtSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface DebtMapper extends MyMapper<Debt> {
    /**
     * 获得该床位的总消费
     */
    @Select("select sum(consume) consume from debt where bed=#{bed}")
    @ResultType(Double.class)
    Double getTotalConsumeByBed(@Param("bed") String bed);

    /**
     * 计算该营业部门在该单子下的销售情况
     */
    @SelectProvider(type = DebtSql.class,method = "getTotalConsumeByPointOfSaleAndSerial")
    @ResultType(Double.class)
    Double getTotalConsumeByPointOfSaleAndSerial(@Param("pointOfSale") String pointOfSale,@Param("serial")String serial);

    /**
     * 通过房间号获得押金数组
     */
    @Select("select * from debt where room_id in (${roomId}) and deposit>0 and ifnull(remark,0)!=\'已退\'")
    List<Debt> getDepositListByRoomList(@Param("roomId") String roomId);

    /**
     * 获得全部的在店押金值
     */
    @Select("select ifnull(sum(deposit),0) from debt where deposit is not null")
    @ResultType(Double.class)
    Double getDepositMoneyAll();

    /**
     * 获得某个币种的在店押金
     */
    @Select("select ifnull(sum(deposit),0) from debt where deposit is not null and currency=#{currency}")
    @ResultType(Double.class)
    Double getDepositMoneyAllByCurrency(@Param("currency")String currency);

    /**
     * 获得全部的在店押金数组
     */
    @Select("select * from debt where deposit is not null")
    List<Debt> getDepositListAll();

    /**
     * 获得该房间的总消费
     */
    @Select("select round(sum(consume),2) from debt where room_id=#{roomId}")
    @ResultType(Double.class)
    Double getTotalConsumeByRoomId(@Param("roomId") String roomId);

    /**
     * 获得该房间的总消费(用于叫回账单，重新统计consume生成checkIn)
     */
    @Select("select sum(consume) from debt where group_account=#{groupAccount}")
    @ResultType(Double.class)
    Double getTotalConsumeByGroupAccount(@Param("groupAccount") String groupAccount);
}
