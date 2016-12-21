package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskPay;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17 0017.
 * 交班审核表不包括营业部门，因为多个营业部门如果都是一个人操作的本身就不合理，所以必须显示出来
 */
public interface DeskPayMapper extends MyMapper<DeskPay> {

    /**
     * 消费额
     */
    /*时间段，币种，操作员*/
    @Select("select sum(pay_money) deskMoney from desk_pay where user_id = #{userId} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} and ifnull(disabled,false)=false")
    Double getDeskMoneyByCurrencyDateUser(@Param("userId") String userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency);

    /*时间段，币种*/
    @Select("select sum(pay_money) deskMoney from desk_pay where done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} and ifnull(disabled,false)=false")
    Double getDeskMoneyByCurrencyDate(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency);

    /*时间段，币种，营业部门*/
    @Select("select ifnull(sum(pay_money),0) deskMoney from desk_pay where point_of_sale = #{pointOfSale} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} and ifnull(disabled,false)=false")
    Double getDeskMoneyByCurrencyDatePointOfSale(@Param("pointOfSale") String pointOfSale, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency);

    /**
     * 获得该日期该币种的消费额(餐饮,不带销售员，带pos点（一级营业部门）)
     */
    @Select("select * from desk_pay where point_of_sale = #{pointOfSale} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} and ifnull(disabled,false)=false")
    @Results(value = {
            @Result(property = "doneTime", column = "done_time"),
            @Result(property = "payMoney", column = "pay_money"),
            @Result(property = "currencyAdd", column = "currency_add"),
            @Result(property = "ckSerial", column = "ck_serial"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "pointOfSale", column = "point_of_sale")
    })
    List<DeskPay> getByCurrencyDatePointOfSale(@Param("pointOfSale") String pointOfSale, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency);

    /**
     * 通过时间段和销售点统计币种
     */
    @Select("select currency,sum(pay_money) as payMoney from desk_pay where point_of_sale = #{pointOfSale} and done_time > #{beginTime} and done_time< #{endTime} and ifnull(disabled,false)=false group by currency")
    List<DeskPay> getByDatePointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    @Update("update desk_pay set disabled =true where ck_serial=#{ckSerial}")
    void setDisabledBySerial(@Param("ckSerial") String ckSerial);
}
