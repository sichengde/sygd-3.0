package com.sygdsoft.mapper;

import com.sygdsoft.model.DeskPay;
import com.sygdsoft.sqlProvider.DeskPaySql;
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
    @SelectProvider(type = DeskPaySql.class,method = "getPay")
    @ResultType(value = Double.class)
    Double getPay(@Param("userId") String userId, @Param("currency") String currency,@Param("pointOfSale") String pointOfSale,@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*时间段，币种，营业部门*/
    @Select("select ifnull(sum(pay_money),0) deskMoney from desk_pay where point_of_sale = #{pointOfSale} and done_time > #{beginTime} and done_time< #{endTime} and currency=#{currency} and ifnull(disabled,false)=false")
    Double getDeskMoneyByCurrencyDatePointOfSale(@Param("pointOfSale") String pointOfSale, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("currency") String currency);

    /**
     * 通过时间段和销售点统计币种
     */
    @Select("select currency,sum(pay_money) as payMoney from desk_pay where point_of_sale = #{pointOfSale} and done_time > #{beginTime} and done_time< #{endTime} and ifnull(disabled,false)=false group by currency")
    List<DeskPay> getByDatePointOfSale(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("pointOfSale") String pointOfSale);

    @Update("update desk_pay set disabled =true where ck_serial=#{ckSerial}")
    void setDisabledBySerial(@Param("ckSerial") String ckSerial);

    /**
     * 获取每日币种
     */
    @Select("SELECT substr(done_time,1,10) doneTime,round(ifnull(sum(pay_money),0),2) payMoney,currency FROM desk_pay WHERE done_time > #{beginTime} AND done_time < #{endTime} GROUP BY substr(done_time,1,10),currency order by substr(done_time,1,10)")
    List<DeskPay> getConsumeEveryDay(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
