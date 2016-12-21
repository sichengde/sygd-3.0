package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskInHistoryMapper;
import com.sygdsoft.model.DeskInHistory;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
@Service
@SzMapper(id = "deskInHistory")
public class DeskInHistoryService extends BaseService<DeskInHistory>{
    @Autowired
    DeskInHistoryMapper deskInHistoryMapper;
    @Autowired
    TimeService timeService;
    @Autowired
    Util util;
    /**
     * 通过结账时间和营业部门筛选
     */
    public List<DeskInHistory> getByDatePointOfSale(Date begin,Date endTime,String pointOfSale){
        Example example=new Example(DeskInHistory.class);
        example.createCriteria().andCondition("done_time>"+util.wrapWithBrackets(timeService.dateToStringLong(begin))+" and done_time<"+util.wrapWithBrackets(timeService.dateToStringLong(endTime))+" and point_of_sale="+util.wrapWithBrackets(pointOfSale)+"and ifnull(disabled,false)=false");
        example.orderBy("done_time").desc();
        return deskInHistoryMapper.selectByExample(example);
    }

    /**
     * 设置冲减标志为true
     */
    public void setDisabled(List<DeskInHistory> deskInHistoryList) throws Exception {
        for (DeskInHistory deskInHistory : deskInHistoryList) {
            deskInHistory.setDisabled(true);
        }
        update(deskInHistoryList);
    }

}
