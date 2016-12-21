package com.sygdsoft.controller;

import com.sygdsoft.jsonModel.Query;
import com.sygdsoft.model.Cargo;
import com.sygdsoft.model.PointOfSale;
import com.sygdsoft.model.RoomShopDetail;
import com.sygdsoft.model.StorageOutDetail;
import com.sygdsoft.service.*;
import com.sygdsoft.util.SzMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 舒展 on 2016-11-17.
 */
@RestController
public class StorageOutDetailController {
    @Autowired
    StorageOutDetailService storageOutDetailService;

    @RequestMapping(value = "storageOutDetailGet")
    public List<StorageOutDetail> storageOutDetailGet(@RequestBody Query query) throws Exception {
        return storageOutDetailService.get(query);
    }

}
