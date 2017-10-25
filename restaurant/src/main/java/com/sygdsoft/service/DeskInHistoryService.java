package com.sygdsoft.service;

import com.sygdsoft.jsonModel.HotelParseLineRow;
import com.sygdsoft.jsonModel.Query;
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
     * 通过结账序列号获取
     */
    public DeskInHistory getByCkSerial(String ckSerial){
        DeskInHistory deskInHistoryQuery=new DeskInHistory();
        deskInHistoryQuery.setCkSerial(ckSerial);
        return deskInHistoryMapper.selectOne(deskInHistoryQuery);
    }
    /**
     * 通过结账时间和营业部门筛选
     */
    public List<DeskInHistory> getByDatePointOfSale(Date begin,Date endTime,String pointOfSale) throws Exception {
        Query query=new Query("done_time>\'"+timeService.dateToStringLong(begin)+"\' and done_time<\'"+timeService.dateToStringLong(endTime)+"\' and point_of_sale=\'"+pointOfSale+"\' and ifnull(disabled,false)=false");
        query.setOrderByListDesc(new String[]{"done_time"});
        return get(query);
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

    /**
     * 获取人数线性统计
     */
    public List<HotelParseLineRow> deskManDateChart(Date beginTime, Date endTime){
        return deskInHistoryMapper.deskManDateChart(beginTime, endTime);
    }

    /**
     * 获取桌台类别消费
     */
    public Double getCategorySum(Date beginTime, Date endTime, String firstPointOfSale, String item) {
        return deskInHistoryMapper.getCategorySum(beginTime, endTime, firstPointOfSale, item);
    }

}
