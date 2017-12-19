package com.sygdsoft.mapper;

import com.sygdsoft.model.Company;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CompanyMapper extends MyMapper<Company> {
    /**
     * 为单位增加消费
     */
    @Update("UPDATE company set consume=ifnull(consume,0)+#{consume} where name=#{company}")
    void addConsume(@Param("company") String company, @Param("consume") Double consume);

    /**
     * 为单位增加挂账
     */
    @Update("UPDATE company set debt=ifnull(debt,0)+#{debt} where name=#{company}")
    void addDebt(@Param("company") String company, @Param("debt") Double debt);

    /**
     * 为单位充值
     */
    @Update("UPDATE company set deposit=ifnull(deposit,0)+#{deposit} where name=#{company}")
    void addDeposit(@Param("company") String company, @Param("deposit") Double deposit);

    /**
     * 结算
     */
    @Update("UPDATE company set debt=ifnull(debt,0)-#{total} where name=#{company}")
    void pay(@Param("company") String company, @Param("total") Double total);

    /**
     * 获取该模块的单位挂账款
     */
    @Select("SELECT sum(debt) FROM company_debt WHERE point_of_sale=#{module} AND company=#{company}")
    @ResultType(Double.class)
    Double getModuleDebt(@Param("module") String module,@Param("company") String company);
}
