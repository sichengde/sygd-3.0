package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.House;
import com.sygdsoft.model.StorageInDetail;
import com.sygdsoft.service.HouseService;
import com.sygdsoft.service.StorageInDetailService;
import com.sygdsoft.service.StorageInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9 0009.
 */
@RestController
public class HouseController {
    @Autowired
    HouseService houseService;
    @Autowired
    StorageInDetailService storageInDetailService;
    @Autowired
    StorageInService storageInService;

    @RequestMapping(value = "houseAdd")
    public void houseAdd(@RequestBody House house) throws Exception {
        houseService.add(house);
    }

    @RequestMapping(value = "houseDelete")
    @Transactional(rollbackFor = Exception.class)
    public void houseDelete(@RequestBody List<House> houseList) throws Exception {
        /*有品种时不能删除*/
        for (House house : houseList) {
            List<StorageInDetail> storageInDetailList = storageInDetailService.getSumNumByHouse(house.getHouseName());
            for (StorageInDetail storageInDetail : storageInDetailList) {
                if(storageInDetail.getRemain()>0){
                    throw new Exception(house.getHouseName()+"库中"+storageInDetail.getCargo()+"品种数量为"+storageInDetail.getRemain()+"不为0不能删除");
                }
            }
        storageInDetailService.deleteByHouse(house.getHouseName());
        }
        houseService.delete(houseList);
    }

    @RequestMapping(value = "houseUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void houseUpdate(@RequestBody List<House> houseList) throws Exception {
        if (houseList.size() > 1) {
            if (houseList.get(0).getId().equals(houseList.get(houseList.size() / 2).getId())) {
                houseService.update(houseList.subList(0, houseList.size() / 2));
                return;
            }
        }
        houseService.update(houseList);
    }

    @RequestMapping(value = "houseGet")
    public List<House> houseGet(@RequestBody Query query) throws Exception {
        return houseService.get(query);
    }

}
