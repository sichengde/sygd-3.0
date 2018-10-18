package com.sygdsoft.mapper;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Vip;
import com.sygdsoft.sqlProvider.VipIntegrationSql;
import com.sygdsoft.sqlProvider.VipSql;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-05-09.
 */
public interface VipMapper extends MyMapper<Vip> {
    /**
     * 注销该会员
     */
    @Update("update vip set state=\'注销\',id_number=null where vip_number=#{vipNumber}")
    void cancel(@Param("vipNumber") String vipNumber);

    /**
     * 更新会员余额（充值或者消费）
     */
    @UpdateProvider(type = VipIntegrationSql.class,method = "updateVipRemain")
    void updateVipRemain(@Param("vipNumber") String vipNumber, @Param("money") Double money,@Param("real") Double real,@Param("withScore") Boolean withScore);

    /**
     * 更新会员积分（充值或者消费）
     */
    @Update("update vip set score=ifnull(score,0)+#{score} where vip_number=#{vipNumber}")
    void updateVipScore(@Param("vipNumber") String vipNumber, @Param("score") Double score);

    /**
     * 开房时冻结押金与结账时解冻
     */
    @Update("update vip set remain=ifnull(remain,0)-#{money},deposit=ifnull(deposit,0)+#{money} where vip_number=#{vipNumber}")
    void depositByVip(@Param("vipNumber") String vipNumber, @Param("money") Double money);

    @Update("UPDATE vip SET score = ifnull(score,0) + #{score} WHERE vip_number=#{vipNumber}")
    void vipAddScore(@Param("vipNumber") String vipNumber, @Param("score") Double score);

    @SelectProvider(type = VipSql.class,method = "get")
    List<Vip> get(@Param("query") Query query);
}
