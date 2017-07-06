package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.OnlyString;
import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.StorageInDetail;
import com.sygdsoft.service.StorageInDetailService;
import com.sygdsoft.service.SzMapper;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Created by 舒展 on 2016-11-17.
 * 入库明细，只有查询
 */
@RestController
public class StorageInDetailController {
    @Autowired
    StorageInDetailService storageInDetailService;
    @Autowired
    SzMath szMath;

    @RequestMapping(value = "storageInDetailGet")
    public List<StorageInDetail> storageInDetailGet(@RequestBody Query query) throws Exception {
        return storageInDetailService.get(query);
    }

    /**
     * 盘点可用货物
     */
    @RequestMapping(value = "getRemainCargo")
    public List<StorageInDetail> getRemainCargo(@RequestBody String houseName){
        return storageInDetailService.getSumNumByHouse(houseName);
    }

    /**
     * 计算平均价格
     */
    @RequestMapping(value = "storageParsePrice")
    public String storageParsePrice(@RequestBody StorageInDetail storageInDetail){
        String cargo=storageInDetail.getCargo();
        Double num=storageInDetail.getNum();
        String house =storageInDetail.getHouse();
        return szMath.formatTwoDecimal(storageInDetailService.storageParsePrice(house, cargo, num));
    }

}
