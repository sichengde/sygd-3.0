package com.sygdsoft.sqlProvider;

import java.util.Map;

public class CheckInSnapshotSql {
    public CheckInSnapshotSql() {
    }
    public String getSum(Map<String, Object> map) {
        String field = (String) map.get("field");
        return "select round(ifnull(sum("+field+"),0),2) from check_in_snapshot where report_date=#{date}";
    }
    public String getCount(Map<String, Object> map) {
        String guestSource = (String) map.get("guestSource");
        String basic="select count(*) from check_in_snapshot where report_date>=#{beginTime} and report_date <#{endTime} ";
        if(guestSource!=null){
            switch (guestSource) {
                case "NULL":
                    basic += " and guest_source is null";
                    break;
                case "NOT NULL":
                    basic += " and guest_source is not null";
                    break;
                default:
                    basic += " and guest_source =#{guestSource}";
                    break;
            }
        }
        return basic;
    }

}
