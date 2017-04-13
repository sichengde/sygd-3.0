package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.*;
import com.sygdsoft.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.sygdsoft.util.NullJudgement.ifNotNullGetString;

/**
 * Created by 舒展 on 2016-11-14.
 */
@RestController
public class StorageInController {
    @Autowired
    StorageInService storageInService;
    @Autowired
    CargoService cargoService;

    @RequestMapping(value = "storageInDelete")
    @Transactional(rollbackFor = Exception.class)
    public void storageInDelete(@RequestBody List<StorageIn> storageInList) throws Exception {
        storageInService.delete(storageInList);
    }

    @RequestMapping(value = "storageInUpdate")
    @Transactional(rollbackFor = Exception.class)
    public void storageInUpdate(@RequestBody List<StorageIn> storageInList) throws Exception {
        if (storageInList.size() > 1) {
            if (storageInList.get(0).getId().equals(storageInList.get(storageInList.size() / 2).getId())) {
                storageInService.update(storageInList.subList(0, storageInList.size() / 2));
                return;
            }
        }
        storageInService.update(storageInList);
    }

    @RequestMapping(value = "storageInGet")
    public List<StorageIn> storageInGet(@RequestBody Query query) throws Exception {
        return storageInService.get(query);
    }

    /**
     * 入库
     */
    @RequestMapping(value = "storageInAdd")
    @Transactional(rollbackFor = Exception.class)
    public Integer storageInAdd(@RequestBody StorageInJson storageInJson) throws Exception {
        List<StorageInDetail> storageInDetailList=storageInJson.getStorageInDetailList();
        StorageIn storageIn=storageInJson.getStorageIn();
        /*检查一下货品是否存在*/
        for (StorageInDetail storageInDetail : storageInDetailList) {
            Cargo cargo=cargoService.getByName(storageInDetail.getCargo());
            if(cargo==null){
                throw new Exception("该货品没有定义");
            }
        }
        return storageInService.storageInAdd(storageInDetailList, storageIn);
    }
}
