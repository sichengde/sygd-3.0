package com.sygdsoft.mapper;

import com.sygdsoft.model.CompanyLord;
import com.sygdsoft.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 舒展 on 2016-04-13.
 */
public interface CompanyLordMapper extends MyMapper<CompanyLord> {
    /**
     * 为单位签单人增加挂账
     */
    @Update("UPDATE company_lord set debt=round(ifnull(debt,0)+#{debt},2) where name=#{lord}")
    void addDebt(@Param("lord") String lord, @Param("debt") Double debt);
}
