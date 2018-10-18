package com.sygdsoft.sqlProvider;

import com.sygdsoft.jsonModel.Query;

import java.util.Map;

public class VipSql {
    public VipSql() {
    }

    public String get(Map<String, Object> parameters){
        String basic="SELECT vip.id id, vip_number vipNumber, card_id cardId, vip.category category, name, sex, address, race, birthday_time birthdayTime, phone, state, score, remain, deposit, do_time doTime, id_number idNumber, user_id userId, currency, remain_time remainTime, work_company workCompany, remark, brothers, protocol, available_pos availablePos FROM vip LEFT JOIN vip_category ON vip.category = vip_category.category";
        Query query=(Query) parameters.get("query");
        if(query!=null){
            if(query.getCondition()!=null) {
                basic += " where "+query.getCondition();
            }
        }
        return basic;
    }
}
