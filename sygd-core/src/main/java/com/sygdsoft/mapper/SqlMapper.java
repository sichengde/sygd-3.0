package com.sygdsoft.mapper;

import com.sygdsoft.sqlProvider.Sql;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * Created by 舒展 on 2016-08-22.
 */
public interface SqlMapper {
    @SelectProvider(type = Sql.class,method = "selectDistinct")
    @ResultType(String.class)
    List<String> getStringList(@Param("select") String select);

    @UpdateProvider(type = Sql.class, method = "sqlUpdate")
    void sqlUpdate(@Param("update") String update);
}
