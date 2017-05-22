package com.sygdsoft.sqlProvider;

import java.util.Map;

/**
 * Created by 舒展 on 2016-08-22.
 * 直接执行select语句
 */
public class Sql {
    public Sql() {
    }

    public String selectDistinct(Map<String, Object> parameters){
        return (String) parameters.get("select");
    }

    public String sqlUpdate(Map<String, Object> parameters){
        return (String) parameters.get("update");
    }
}
