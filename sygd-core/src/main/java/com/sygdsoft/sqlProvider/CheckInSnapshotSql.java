package com.sygdsoft.sqlProvider;

import java.util.Map;

public class CheckInSnapshotSql {
    public CheckInSnapshotSql() {
    }
    public String getSum(Map<String, Object> map) {
        String field = (String) map.get("field");
        return "select round(ifnull(sum("+field+"),0),2) from check_in_snapshot where report_date=#{date}";
    }
}
