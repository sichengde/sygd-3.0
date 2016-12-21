package com.sygdsoft.service;

import com.sygdsoft.model.SaunaIn;
import com.sygdsoft.model.SaunaRing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 舒展 on 2016-12-01.
 */
@Service
@SzMapper(id = "saunaRing")
public class SaunaRingService extends BaseService<SaunaRing> {
    @Autowired
    SaunaInService saunaInService;

    public void setRingDetail(List<SaunaRing> saunaRingList) {
        for (SaunaRing saunaRing : saunaRingList) {
            if (saunaRing.getNotNullRepair()) {
                saunaRing.setState("维修");
            } else {
                SaunaIn saunaIn = saunaInService.getByRing(saunaRing.getNumber());
                if (saunaIn == null) {
                    saunaRing.setState("未用");
                } else {
                    saunaRing.setState("占用");
                    saunaRing.setSaunaIn(saunaInService.getByRing(saunaRing.getNumber()));
                }
            }
        }
    }
}
