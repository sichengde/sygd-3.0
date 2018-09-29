package com.sygdsoft.sqlProvider;

import com.sygdsoft.jsonModel.Query;

import java.util.Map;

public class VipSql {
    public VipSql() {
    }

    public String get(Map<String, Object> parameters){
        String basic="SELECT vip.id id, vip_number, card_id, vip.category category, name, sex, address, race, birthday_time, phone, state, score, remain, deposit, do_time, id_number, user_id, currency, remain_time, work_company, remark, brothers, protocol, available_pos FROM vip LEFT JOIN vip_category ON vip.category=vip_category.category;";
        Query query=(Query) parameters.get("query");
        if(query!=null){
            if(query.getCondition()!=null) {
                basic += " where "+query.getCondition();
            }
        }
        return basic;
    }
}
