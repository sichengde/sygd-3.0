package com.sygdsoft.sqlProvider;

import java.util.Date;
import java.util.Map;

/**
 * Created by 舒展 on 2017-03-28.
 */
public class GuestIntegrationSql {
    public GuestIntegrationSql() {
    }

    public String getList(Map<String, Object> parameters){
        String basic="SELECT count(*) total,ifnull(addr,'未定义') addr,sum(if_in) now FROM ( SELECT   card_id,   concat(country,ifnull(city,'')) addr,   self_account,   reach_time,   if_in, city FROM guest_integration LEFT JOIN card_map_city ON card_id LIKE concat(card,'%') ";
        Date beginTime=(Date) parameters.get("beginTime");
        Date endTime=(Date) parameters.get("endTime");
        if(beginTime!=null){
            basic+="where reach_time>#{beginTime} ";
            if (endTime != null) {
                basic += "and reach_time<#{endTime} ";
            }
        }else {
            if (endTime != null) {
                basic += "where reach_time<#{endTime} ";
            }
        }
        basic+=" ) AS detail GROUP BY addr;";
        return basic;
    }

    public String getSumNum(Map<String,Object> map){
        Date beginTime=(Date) map.get("beginTime");
        Date endTime=(Date) map.get("endTime");
        String guestSource=(String) map.get("guestSource");
        String cardIdFirstFour=(String) map.get("cardIdFirstFour");
        Boolean like=(Boolean) map.get("like");
        Boolean foreigner=(Boolean) map.get("foreigner");
        String basic="SELECT count(*) FROM guest_integration ";
        String add="";
        if(beginTime!=null){
            add += " and reach_time>#{beginTime} ";
        }
        if(endTime!=null){
            add += " and reach_time<#{endTime} ";
        }
        if(guestSource!=null){
            add+=" and guest_source=#{guestSource}";
        }
        if(cardIdFirstFour!=null){
            if(like){
                add+=" and card_id LIKE #{cardIdFirstFour}";
            }else {
                add+=" and card_id NOT LIKE #{cardIdFirstFour}";
            }
        }
        if(foreigner!=null){
            if(foreigner){
                add+=" and ifnull(country,'中国')!='中国'";
            }
        }
        if(!"".equals(add)){
            basic+=" where "+add.substring(4,add.length());
        }
        return basic;
    }

}
