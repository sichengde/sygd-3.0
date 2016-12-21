package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Cargo;
import com.sygdsoft.model.House;
import com.sygdsoft.model.StorageInDetail;
import com.sygdsoft.service.CargoService;
import com.sygdsoft.service.HouseService;
import com.sygdsoft.service.StorageInDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
@RestController
public class CargoController {
    @Autowired
    CargoService cargoService;
    @Autowired
    HouseService houseService;
    @Autowired
    StorageInDetailService storageInDetailService;

    @RequestMapping(value = "cargoAdd")
    public void cargoAdd(@RequestBody Cargo cargo) throws Exception {
        cargoService.add(cargo);
    }

    @RequestMapping(value = "cargoDelete")
    @Transactional(rollbackFor = Exception.class)
    public void cargoDelete(@RequestBody List<Cargo> cargoList) throws Exception {
        /*有品种时不能删除*/
        for (Cargo cargo : cargoList) {
            List<StorageInDetail> storageInDetailList = storageInDetailService.getByCargo(cargo.getName());
            for (StorageInDetail storageInDetail : storageInDetailList) {
                if(storageInDetail.getRemain()>0){
                    throw new Exception(storageInDetail.getHouse()+"库中"+storageInDetail.getCargo()+"品种数量为"+storageInDetail.getRemain()+"不为0不能删除");
                }
            }
        storageInDetailService.delete(storageInDetailList);
        }
        cargoService.delete(cargoList);
    }

    @RequestMapping(value = "cargoUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void cargoUpdate(@RequestBody List<Cargo> cargoList) throws Exception {
        if (cargoList.size() > 1) {
            if (cargoList.get(0).getId().equals(cargoList.get(cargoList.size() / 2).getId())) {
                cargoService.update(cargoList.subList(0, cargoList.size() / 2));
                return;
            }
        }
        cargoService.update(cargoList);
    }

    @RequestMapping(value = "cargoGet")
    public List<Cargo> cargoGet(@RequestBody Query query) throws Exception {
        return cargoService.get(query);
    }

    @RequestMapping(value = "cargoGetByHouse")
    public List<Cargo> cargoGetByHouse(@RequestBody String houseName) throws Exception{
        House house=houseService.getByHouseName(houseName);
        String[] categoryList=house.getIncludeCategory().split(",");
        List<Cargo> totalCargoList=new ArrayList<>();
        for (String category : categoryList) {
            totalCargoList.addAll(cargoService.getListByCategory(category));
        }
        return totalCargoList;
    }
}
