package com.sygdsoft.service;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.mapper.NightDateMapper;
import com.sygdsoft.model.NightDate;
import com.sygdsoft.util.Util;
import org.joda.time.field.UnsupportedDateTimeField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-04-08.
 */
@Service
@SzMapper(id = "nightDate")
public class NightDateService extends BaseService<NightDate> {
    @Autowired
    TimeService timeService;
    @Autowired
    NightDateMapper nightDateMapper;
    @Autowired
    Util util;

    /**
     * 设置该次的夜审对应时间
     */
    public void setNightAction(Date beforeDate) {
        NightDate nightDate = new NightDate();
        nightDate.setNightDate(timeService.addDay(beforeDate, 1));
        nightDate.setNightTime(timeService.getNow());
        nightDateMapper.insert(nightDate);
    }

    /**
     * 通过夜审日期获取该日期夜审的时间
     */
    public Date getByDate(Date date) throws Exception {
        NightDate nightDate = new NightDate();
        nightDate.setNightDate(date);
        Query query=new Query("night_date="+util.wrapWithBrackets(timeService.dateToStringShort(date)));
        query.setOrderByListDesc(new String[]{"night_time"});
        List<NightDate> nightDateList= get(query);
        /*降序排列，考虑有误操作存在多个时间*/
        if(nightDateList.size()==0){
            /*如果获取不到该日期的夜审时间，则需要假定一个，为当前时间减一天*/
            return timeService.addDay(timeService.getNow(),-1);
        }else {
        return nightDateList.get(0).getNightTime();
        }
    }
}
