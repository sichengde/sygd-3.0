package com.sygdsoft.service;

import com.sygdsoft.mapper.DeskBookMapper;
import com.sygdsoft.model.DeskBook;
import com.sygdsoft.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by 舒展 on 2016-09-13.
 */
@Service
@SzMapper(id = "deskBook")
public class DeskBookService extends BaseService<DeskBook> {
    @Autowired
    DeskBookMapper deskBookMapper;
    @Autowired
    Util util;
    /**
     * 模糊查询,查询该房号是否有预定
     */
    List<DeskBook> getListByDesk(String desk){
        return deskBookMapper.getLastThreeByDesk(desk);
    }

    /**
     * 补交/退订金
     */
    public void addSubscription(String deskBookSerial, Double subscription) {
       deskBookMapper.addSubscription(deskBookSerial,subscription);
    }

}
