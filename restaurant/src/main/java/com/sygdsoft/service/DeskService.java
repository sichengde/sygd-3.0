package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskInMapper;
import com.sygdsoft.mapper.DeskMapper;
import com.sygdsoft.model.Desk;
import com.sygdsoft.model.DeskIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-07-14.
 */
@Service
@SzMapper(id = "desk")
public class DeskService extends BaseService<Desk> {
    @Autowired
    DeskMapper deskMapper;
    @Autowired
    DeskInMapper deskInMapper;
    @Autowired
    DeskBookService deskBookService;

    /**
     * 设置桌台信息
     */
    public void setDeskDetail(List<Desk> deskList) {
        for (Desk desk : deskList) {
            DeskIn deskInQuery = new DeskIn();
            deskInQuery.setDesk(desk.getName());
            desk.setDeskIn(deskInMapper.selectOne(deskInQuery));
            desk.setDeskBookList(deskBookService.getListByDesk(desk.getName()));
        }
    }

    /**
     * 通过桌号和销售点获得桌台信息
     */
    public DeskIn getByDesk(String desk, String pointOfSale){
        DeskIn deskIn=new DeskIn();
        deskIn.setDesk(desk);
        deskIn.setPointOfSale(pointOfSale);
        return deskInMapper.selectOne(deskIn);
    }

    /**
     * 获取总计座位数
     */
    public Integer getTotalSeat(String pointOfSale){
        return deskMapper.getTotalSeat(pointOfSale);
    }
}
